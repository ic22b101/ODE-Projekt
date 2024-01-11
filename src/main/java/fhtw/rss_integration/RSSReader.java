package fhtw.rss_integration;

import com.opencsv.CSVWriter;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.HttpClientFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import javafx.application.Platform;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RSSReader {

    private List<SyndEntry> entries;

    private static final Logger log = LoggerFactory.getLogger(RSSReader.class);
    private RSSFeedDisplayController feedDisplayController;
    // Hinzugefügt: Methode, um die Liste der Einträge zurückzugeben

    // Hinzugefügt: Speichert die Liste der Einträge

    public void setFeedDisplayController(RSSFeedDisplayController feedDisplayController) {
        this.feedDisplayController = feedDisplayController;
    }

    public void setUpdateFrequency(String updateFrequency) {
        log.info("Aktualisierungshäufigkeit gesetzt: {}", updateFrequency);
    }

    public void readAndPrintRSSFeed() {
        String feedUrl = "https://www.derstandard.at/rss";

        try {
            URL url = new URL(feedUrl);
            HttpClientFeedFetcher feedFetcher = new HttpClientFeedFetcher();
            SyndFeed feed = feedFetcher.retrieveFeed(url);

            entries = feed.getEntries();  // Hinzugefügt: Setze die Liste der Einträge

            // RSS-Feeds anzeigen in der GUI
            displayRSSFeedsInGUI(entries);

            // CSV-Output generieren
            generateCSVOutput(entries, "output.csv");

        } catch (FeedException | FetcherException | MalformedURLException e) {
            // Handle or log the exceptions appropriately
            log.error("Error while fetching or processing the RSS feed", e);
        } catch (IOException e) {
            // Handle or log IOException
            log.error("IO error while processing the RSS feed", e);
        }
    }

    private void displayRSSFeedsInGUI(List<SyndEntry> entries) {
        // Anzeige der RSS-Feeds in der GUI
        if (feedDisplayController != null) {
            System.out.println("Displaying RSS Feeds in GUI...");
            Platform.runLater(() -> {
                System.out.println("Platform.runLater: Displaying RSS Feeds...");
                feedDisplayController.displayRSSFeeds(entries);
            });
        } else {
            System.out.println("feedDisplayController is null");
        }
    }

    private void generateCSVOutput(List<SyndEntry> entries, String outputFile) {
        log.info("CSV-Datei wird erstellt: {}", outputFile);
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
            // Header-Zeile schreiben
            String[] header = {"Title", "Link", "Published Date", "Description"};
            writer.writeNext(header);

            // Daten für jeden Eintrag schreiben
            for (SyndEntry entry : entries) {
                String[] data = {
                        entry.getTitle(),
                        entry.getLink(),
                        String.valueOf(entry.getPublishedDate()),
                        cleanHtmlTags(entry.getDescription().getValue())
                };
                writer.writeNext(data);
            }

            log.info("CSV-Datei erfolgreich erstellt: {}", outputFile);

        } catch (IOException e) {
            log.error("Fehler beim Generieren der CSV-Ausgabe", e);
            outputFile = "default_output.csv";
            log.warn("Standardwert für outputFile wird verwendet: {}", outputFile);
        }
    }

    private String cleanHtmlTags(String html) {
        Document document = Jsoup.parse(html);
        return Jsoup.clean(document.body().html(), Whitelist.none());
    }

    // Hinzugefügt: Methode, um die Liste der Einträge zurückzugeben
    public List<SyndEntry> getEntries() {
        return entries;
    }
}

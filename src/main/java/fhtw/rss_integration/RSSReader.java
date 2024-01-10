package fhtw.rss_integration;


import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class RSSReader {

    public void readAndPrintRSSFeed() {
        String feedUrl = "https://www.derstandard.at/rss";

        try {
            URL url = new URL(feedUrl);
            FeedFetcher feedFetcher = new HttpURLFeedFetcher();
            SyndFeed feed = feedFetcher.retrieveFeed(url);

            List<SyndEntry> entries = feed.getEntries();

            // RSS-Feeds drucken
            printRSSFeeds(entries);

            // CSV-Output generieren
            generateCSVOutput(entries, "output.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printRSSFeeds(List<SyndEntry> entries) {
        for (SyndEntry entry : entries) {
            System.out.println("Title: " + entry.getTitle());
            System.out.println("Link: " + entry.getLink());
            System.out.println("Published Date: " + entry.getPublishedDate());
            System.out.println("Description: " + cleanHtmlTags(entry.getDescription().getValue()));
            System.out.println("------------------------------");
        }
    }

    private void generateCSVOutput(List<SyndEntry> entries, String outputFile) {
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

            System.out.println("CSV-Datei erfolgreich erstellt: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String cleanHtmlTags(String html) {
        Document document = Jsoup.parse(html);
        return Jsoup.clean(document.body().html(), Whitelist.none());
    }
}

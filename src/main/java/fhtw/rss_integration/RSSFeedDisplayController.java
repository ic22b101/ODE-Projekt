package fhtw.rss_integration;

import com.rometools.rome.feed.synd.SyndEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jsoup.Jsoup;

import java.util.List;

public class RSSFeedDisplayController {

    @FXML
    private TextArea rssTextArea;
    private Stage feedDisplayStage;

    public void displayRSSFeeds(List<SyndEntry> entries) {
        System.out.println("Displaying RSS Feeds. Number of entries: " + entries.size());
        StringBuilder rssText = new StringBuilder();
        for (SyndEntry entry : entries) {
            rssText.append("Title: ").append(entry.getTitle()).append("\n");
            rssText.append("Link: ").append(entry.getLink()).append("\n");
            rssText.append("Published Date: ").append(entry.getPublishedDate()).append("\n");
            rssText.append("Description: ").append(cleanHtmlTags(entry.getDescription().getValue())).append("\n");
            rssText.append("------------------------------\n");
        }

        // Anzeigen in der GUI
        rssTextArea.setText(rssText.toString());
    }

    public void showFeedDisplayStage() {
        if (feedDisplayStage != null) {
            feedDisplayStage.show();
        }
    }

    // Neue Methode zum Aktualisieren der Feed-Anzeige
    public void updateRSSFeed(List<SyndEntry> entries) {
        // Hier können Sie die Anzeige der Feed-Artikel aktualisieren
        Platform.runLater(() -> displayRSSFeeds(entries));
    }

    private String cleanHtmlTags(String html) {
        // Verwende Jsoup, um HTML-Tags zu entfernen
        return Jsoup.parse(html).text();
    }
}

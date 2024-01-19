package fhtw.rss_integration;

import com.rometools.rome.feed.synd.SyndEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;

import java.io.IOException;
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

        // Anzeigen in der Konsole
        System.out.println("RSS Text:\n" + rssText.toString());

        // Anzeigen in der GUI
        rssTextArea.setText(rssText.toString());
    }


    public void showFeedDisplayStage() {
        if (feedDisplayStage == null) {
            try {
                // Wenn die Stage noch nicht erstellt wurde, erstellen Sie sie
                feedDisplayStage = new Stage();
                feedDisplayStage.setTitle("RSS Feed Display");

                // Laden Sie das FXML und setzen Sie die Szene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fhtw/RSSFeedDisplay.fxml"));
                VBox root = loader.load();
                Scene scene = new Scene(root);

                // Setzen Sie die Szene für die Stage
                feedDisplayStage.setScene(scene);

                // Übernehmen Sie die Controller-Referenz
                RSSFeedDisplayController displayController = loader.getController();
                displayController.setFeedDisplayStage(feedDisplayStage);
            } catch (IOException e) {
                // Behandeln Sie die IOException oder geben Sie sie weiter
                e.printStackTrace(); // Hier können Sie die Exception behandeln oder weitergeben
            }
        }

        // Zeigen Sie die Stage an
        feedDisplayStage.show();
    }





    private void setFeedDisplayStage(Stage feedDisplayStage) {
        this.feedDisplayStage = feedDisplayStage;
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

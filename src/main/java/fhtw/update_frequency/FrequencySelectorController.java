package fhtw.update_frequency;

import com.rometools.rome.feed.synd.SyndEntry;
import fhtw.rss_integration.RSSFeedDisplayController;
import fhtw.rss_integration.RSSReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


import java.util.List;

public class FrequencySelectorController {

    @FXML
    private ComboBox<String> frequencyComboBox;

    private RSSReader rssReader;
    private RSSFeedDisplayController feedDisplayController;

    public void init(RSSReader rssReader, RSSFeedDisplayController feedDisplayController) {
        this.rssReader = rssReader;
        this.feedDisplayController = feedDisplayController;

        // Fülle die ComboBox mit Frequenzen
        ObservableList<String> frequencyOptions = FXCollections.observableArrayList(
                "15 minutes", "30 minutes", "1 hour", "6 hours", "12 hours", "24 hours"
        );
        frequencyComboBox.setItems(frequencyOptions);

        // Setze die Standardfrequenz
        frequencyComboBox.getSelectionModel().selectFirst();
    }


    @FXML
    private void applyFrequency() {
        if (rssReader != null && feedDisplayController != null) {
            String selectedFrequency = frequencyComboBox.getValue();
            rssReader.setUpdateFrequency(selectedFrequency);
            rssReader.readAndPrintRSSFeed();

            // Holen Sie die Einträge aus dem RSSReader
            List<SyndEntry> entries = rssReader.getEntries();

            // Schließe das FrequencySelector-Fenster
            Stage stage = (Stage) frequencyComboBox.getScene().getWindow();
            stage.close();

            // Rufen Sie die Methode zum Aktualisieren der Feed-Anzeige auf
            feedDisplayController.updateRSSFeed(entries);
            feedDisplayController.showFeedDisplayStage(); // Hier rufen Sie die Methode auf, um das Feed-Anzeige-Fenster zu zeigen
        } else {
            System.out.println("RSSReader oder FeedDisplayController ist null.");
        }
    }
}

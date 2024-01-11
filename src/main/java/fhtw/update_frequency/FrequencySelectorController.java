package fhtw.update_frequency;

import fhtw.rss_integration.RSSReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class FrequencySelectorController {

    @FXML
    private ComboBox<String> frequencyComboBox;

    private RSSReader rssReader;

    public FrequencySelectorController() {
    }

    public void init(RSSReader rssReader) {
        this.rssReader = rssReader;
        // Fülle die ComboBox mit Frequenzen
        ObservableList<String> frequencyOptions = FXCollections.observableArrayList(
                "15 minutes", "30 minutes", "1 hour", "6 hours", "12 hours", "24 hours"
        );
        frequencyComboBox.setItems(frequencyOptions);
    }

    @FXML
    private void applyFrequency() {
        if (this.rssReader != null) {
            String selectedFrequency = frequencyComboBox.getValue();
            this.rssReader.setUpdateFrequency(selectedFrequency);
            this.rssReader.readAndPrintRSSFeed();

            // Schließe das Frequenz-Auswahl-Fenster nach der Anwendung.
            Stage stage = (Stage) frequencyComboBox.getScene().getWindow();
            stage.close();
        } else {
            // Handle den Fall, wenn rssReader null ist
            System.out.println("RSSReader ist null.");
        }
    }


}

package fhtw;

import fhtw.rss_integration.RSSFeedDisplayController;
import fhtw.update_frequency.FrequencySelectorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fhtw.rss_integration.RSSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Laden des FrequencySelector-Fensters
            FXMLLoader frequencyLoader = new FXMLLoader(getClass().getResource("FrequencySelector.fxml"));
            Scene frequencyScene = new Scene(frequencyLoader.load(), 400, 300);

            // Initialisierung des RSSReaders
            RSSReader rssReader = new RSSReader();
            FXMLLoader feedLoader = new FXMLLoader(getClass().getResource("RSSFeedDisplay.fxml"));
            VBox feedDisplayRoot = feedLoader.load();
            RSSFeedDisplayController feedDisplayController = feedLoader.getController();
            rssReader.setFeedDisplayController(feedDisplayController);

            // Laden des RSSFeedDisplay-Fensters
            Scene feedDisplayScene = new Scene(feedDisplayRoot, 600, 400);

            // Initialisierung des FrequencySelector-Controllers
            FrequencySelectorController frequencyController = frequencyLoader.getController();
            frequencyController.init(rssReader, feedDisplayController);

            // Setzen der Szene für das FrequencySelector-Fenster
            primaryStage.setScene(frequencyScene);

            // Zeige das FrequencySelector-Fenster
            primaryStage.setTitle("Frequency Selector");
            primaryStage.show();

            // Fehlerbehandlung für FXML-Lade- oder Initialisierungsfehler
        } catch (IOException e) {
            log.error("Error loading FXML file or initializing controllers", e);
            // Hier können entsprechende Fehlermeldungen oder Aktionen hinzugefügt werden
        }
    }


}




package fhtw;

import fhtw.update_frequency.FrequencySelectorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrequencySelector.fxml"));
            Scene scene = new Scene(loader.load(), 300, 100);

            // Initialisiere den RSSReader (falls benötigt)
            RSSReader rssReader = new RSSReader(); // Hier entsprechend deiner Implementierung
            FrequencySelectorController controller = loader.getController();
            controller.init(rssReader);

            // Setze die Szene für die Hauptbühne (primary stage)
            primaryStage.setScene(scene);

            // Zeige die Hauptbühne
            primaryStage.setTitle("Main Window");
            primaryStage.show();

        } catch (IOException e) {
            log.error("Error loading FXML file or initializing RSSReader", e);
        }
    }
}

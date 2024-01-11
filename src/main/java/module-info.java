module fhtw.odenew {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.opencsv;
    requires com.rometools.rome;
    requires rome.fetcher;
    requires java.desktop;
    requires org.slf4j;
    exports fhtw.rss_integration;


    opens fhtw to javafx.fxml;
    exports fhtw;
    exports fhtw.update_frequency;
    opens fhtw.update_frequency to javafx.fxml;
}
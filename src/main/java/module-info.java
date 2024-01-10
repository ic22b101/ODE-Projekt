module fhtw.odenew {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.opencsv;
    requires com.rometools.rome;
    requires rome.fetcher;


    opens fhtw to javafx.fxml;
    exports fhtw;
}
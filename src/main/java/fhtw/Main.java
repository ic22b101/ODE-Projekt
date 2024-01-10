package fhtw;

import fhtw.rss_integration.RSSReader;



public class Main {
    public static void main(String[] args) {
        RSSReader rssReader = new RSSReader();
        rssReader.readAndPrintRSSFeed();
    }
}

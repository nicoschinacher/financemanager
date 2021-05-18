package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    @Test
    void addAssetNullSymbol() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset(null, "Tesla", "NASDAQ");
        portfolio.addAssetList(asset);
        Assertions.assertEquals(0, portfolio.getAssetList().size());
    }
    @Test
    void addAssetNullCompany() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset("TSLA", null, "NASDAQ");
        portfolio.addAssetList(asset);
        Assertions.assertEquals(0, portfolio.getAssetList().size());
    }
    @Test
    void addAssetNullExchange() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset("TSLA", "Tesla", null);
        portfolio.addAssetList(asset);
        Assertions.assertEquals(0, portfolio.getAssetList().size());
    }
    @Test
    void addValidAsset() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset("TSLA", "Tesla", "NASDAQ");
        portfolio.addAssetList(asset);
        Assertions.assertEquals(1, portfolio.getAssetList().size());
    }
    @Test
    void addEntryInvalidTimeFormat() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset("TSLA", "Tesla", "NASDAQ");
        portfolio.addAssetList(asset);
        Entry entry = new Entry(asset, "2020.12-20", EntryAction.BUY, 10, 500);
        portfolio.addEntryList(entry);
        Assertions.assertEquals(0, portfolio.getEntryList().size());
    }
    @Test
    void addEntryInvalidAsset() {
        Portfolio portfolio = new Portfolio();
        Entry entry = new Entry(null, "2020-12-20", EntryAction.BUY, 10, 500);
        portfolio.addEntryList(entry);
        Assertions.assertEquals(0, portfolio.getEntryList().size());
    }
    @Test
    void addValidEntry() {
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset("TSLA", "Tesla", "NASDAQ");
        portfolio.addAssetList(asset);
        Entry entry = new Entry(asset, "2020-12-20", EntryAction.BUY, 10, 500);
        portfolio.addEntryList(entry);
        Assertions.assertEquals(1, portfolio.getEntryList().size());
    }
}
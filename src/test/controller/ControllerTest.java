package controller;

import model.Asset;
import model.AssetHistory;
import model.Entry;
import model.EntryAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ControllerTest {

    @Test
    void zeroNetLiquidationValue() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Assertions.assertEquals(0, portfolioManager.getNetLiquidationValue());
    }
    @Test
    void getNetLiquidationValue() {
        // Buy 10 stocks @500, net liquidation should be 5000
        PortfolioManager portfolioManager = new PortfolioManager();
        Asset asset = new Asset("TSLA", "Tesla", "NASDAQ");
        asset.addAssetHistory(new AssetHistory("2020-12-20", 500));
        portfolioManager.addEntry(new Entry(asset, "2020-12-20", EntryAction.BUY, 10, 500));
        Assertions.assertEquals(5000, portfolioManager.getNetLiquidationValue());
    }
}
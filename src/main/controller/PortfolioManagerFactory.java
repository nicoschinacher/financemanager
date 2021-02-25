package controller;

import mock.SampleService;
import model.Asset;
import model.Entry;

public class PortfolioManagerFactory {

    public static PortfolioManager createPortfolioManager() {
        PortfolioManager portfolioManager = new PortfolioManager();
        for (Asset asset : SampleService.GENERATE_SAMPLE_ASSETS()) {
            portfolioManager.addAssetList(asset);
        }
        for (Entry entry : SampleService.GENERATE_SAMPLE_ENTRIES(portfolioManager.getAssetList())) {
            portfolioManager.addEntryList(entry);
        }
        return portfolioManager;
    }
}
package model;

import helpers.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Portfolio {

    private final List<Entry> entryList;

    private final List<Asset> assetList;

    private final List<Integer> performanceList;

    public Portfolio() {
        this.entryList = new ArrayList<>();
        this.assetList = new ArrayList<>();
        this.performanceList = new ArrayList<>();

    }
    public List<Entry> getEntryList() {
        return entryList;
    }
    public List<Asset> getAssetList() {
        return assetList;
    }
    public List<Integer> getPerformanceList() {
        return performanceList;
    }
    public void addEntryList(Entry entry) {
        if(entry.getAsset() == null
            || entry.getDate() == null
            || entry.getEntryAction() == null
            || !Validator.isValidFormat("yyyy-MM-dd", entry.getDate(), Locale.GERMANY)) {
            return;
        }
        entryList.add(entry);
    }
    public void addAssetList(Asset asset) {
        if(asset.getSymbol() == null
            || asset.getExchange() == null
            || asset.getCompany() == null) {
            return;
        }
        assetList.add(asset);
    }
    public void addPerformanceList(int performance) {
        performanceList.add(performance);
    }
}
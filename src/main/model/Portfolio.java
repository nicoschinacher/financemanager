package model;

import java.util.ArrayList;
import java.util.List;

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
        entryList.add(entry);
    }
    public void addAssetList(Asset asset) {
        assetList.add(asset);
    }
    public void addPerformanceList(int performance) {
        performanceList.add(performance);
    }
}
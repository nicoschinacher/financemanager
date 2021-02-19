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
        this.performanceList.add(0);
    }
    public void addEntry(Entry entry) {
        if(!Validator.isValidFormat("yyyy-MM-dd", entry.getDate(), Locale.GERMANY)) {
            return;
        }
        entryList.add(entry);
    }
    public void addAllEntry(List<Entry> entryList) {
        for (Entry entry : entryList) {
            addEntry(entry);
        }
    }
    public void addAsset(Asset tempAsset) {
        boolean exists = false;
        for (Asset asset : assetList) {
            if (tempAsset.getSymbol().equals(asset.getSymbol())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            assetList.add(tempAsset);
        }
    }
    public void addAllAsset(List<Asset> tempAssetList) {
        for(Asset tempAsset : tempAssetList) {
            boolean exists = false;
            for(Asset asset : assetList) {
                if (tempAsset.getSymbol().equals(asset.getSymbol())) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                assetList.add(tempAsset);
            }
        }
    }
    public List<Entry> getEntryList(Asset asset) {
        List<Entry> tempEntries = new ArrayList<>();
        for(Entry entry : entryList) {
            if(entry.getAsset() == asset) {
                tempEntries.add(entry);
            }
        }
        return tempEntries;
    }
    public Asset getAsset(String symbol) {
        for (Asset asset : assetList) {
            if(asset.getSymbol().equals(symbol)) {
                return asset;
            }
        }
        return null;
    }
    public List<Asset> getAssetList() {
        return assetList;
    }
    public int getNetLiquidationValue() {
        int assetsValue = 0;
        for (Entry entry : entryList) {
            if (entry.getEntryAction() == EntryAction.BUY) {
                assetsValue += entry.getNumber() * entry.getAsset().getLatestAssetHistory().getPrice();
            } else if (entry.getEntryAction() == EntryAction.SELL) {
                assetsValue -= entry.getNumber() * entry.getPrice();
            }
        }
        performanceList.add(assetsValue);
        return assetsValue;
    }
    public List<Integer> getPerformanceList() {
        return performanceList;
    }
}
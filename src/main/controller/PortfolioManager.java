package controller;

import helpers.Validator;
import model.Asset;
import model.Entry;
import model.EntryAction;
import model.Portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PortfolioManager extends Portfolio {

    public PortfolioManager() {
        getPerformanceList().add(0);
    }
    public Asset getAsset(String symbol) {
        for (Asset asset : getAssetList()) {
            if(asset.getSymbol().equals(symbol)) {
                return asset;
            }
        }
        return null;
    }
    public List<Entry> getEntryListAsset(Asset asset) {
        List<Entry> tempEntries = new ArrayList<>();
        for(Entry entry : getEntryList()) {
            if(entry.getAsset() == asset) {
                tempEntries.add(entry);
            }
        }
        return tempEntries;
    }
    public void addEntry(Entry entry) {
        if(!Validator.isValidFormat("yyyy-MM-dd", entry.getDate(), Locale.GERMANY)) {
            return;
        }
        addEntryList(entry);
    }
    public void addAllEntry(List<Entry> entryList) {
        for (Entry entry : entryList) {
            addEntry(entry);
        }
    }
    public void addAsset(Asset tempAsset) {
        boolean exists = false;
        for (Asset asset : getAssetList()) {
            if (tempAsset.getSymbol().equals(asset.getSymbol())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            getAssetList().add(tempAsset);
        }
    }
    public void addAllAsset(List<Asset> tempAssetList) {
        for(Asset tempAsset : tempAssetList) {
            boolean exists = false;
            for(Asset asset : getAssetList()) {
                if (tempAsset.getSymbol().equals(asset.getSymbol())) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                getAssetList().add(tempAsset);
            }
        }
    }
    public int getNetLiquidationValue() {
        int assetsValue = 0;
        for (Entry entry : getEntryList()) {
            if (entry.getEntryAction() == EntryAction.BUY) {
                assetsValue += entry.getNumber() * entry.getAsset().getLatestAssetHistory().getPrice();
            } else if (entry.getEntryAction() == EntryAction.SELL) {
                assetsValue -= entry.getNumber() * entry.getPrice();
            }
        }
        addPerformanceList(assetsValue);
        return assetsValue;
    }
}
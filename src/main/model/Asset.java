package model;

import helpers.Validator;

import java.util.*;

public class Asset {

    private final String symbol;

    private final String company;
    private final String exchange;

    private final List<AssetHistory> assetHistoryList;

    public Asset(String symbol, String company, String exchange) {
        this.symbol = symbol;
        this.company = company;
        this.exchange = exchange;
        this.assetHistoryList = new ArrayList<>();
    }
    public void addAssetHistory(AssetHistory assetHistory) {
        if(!Validator.isValidFormat("yyyy-MM-dd", assetHistory.getDate(), Locale.GERMANY)) {
            return;
        }
        // Only add asset history if it is after the latest date
        if(getLatestAssetHistory() == null) {
            assetHistoryList.add(assetHistory);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(getLatestAssetHistory().getDate().split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(getLatestAssetHistory().getDate().split("-")[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getLatestAssetHistory().getDate().split("-")[2]));
        Date latestDate = calendar.getTime();
        calendar.set(Calendar.YEAR, Integer.parseInt(assetHistory.getDate().split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(assetHistory.getDate().split("-")[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(assetHistory.getDate().split("-")[2]));
        Date tempDate = calendar.getTime();
        if(tempDate.after(latestDate)) {
            assetHistoryList.add(assetHistory);
        }
    }
    public String getSymbol() {
        return symbol;
    }
    public String getCompany() {
        return company;
    }
    public String getExchange() {
        return exchange;
    }
    public AssetHistory getLatestAssetHistory() {
        if(assetHistoryList.size() == 0) {
            return null;
        }
        return assetHistoryList.get(assetHistoryList.size() - 1);
    }
    public List<AssetHistory> getAssetHistoryList() {
        return assetHistoryList;
    }
    @Override
    public String toString() {
        return getExchange() + ":" + getSymbol();
    }
}
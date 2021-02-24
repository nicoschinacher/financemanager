package model;

import java.util.HashMap;

public class AssetHistory {

    private final String date;

    private final int price;

    public AssetHistory(String date, int price) {
        this.date = date;
        this.price = price;
    }
    public String getDate() {
        return date;
    }
    public int getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return date + " @ " + price + "€";
    }
    @Override
    public boolean equals(Object object) {
        if(!(object instanceof AssetHistory)) {
            return false;
        }
        AssetHistory assetHistory = (AssetHistory) object;
        return assetHistory.getDate().equals(getDate())
                && assetHistory.getPrice() == getPrice();
    }
}
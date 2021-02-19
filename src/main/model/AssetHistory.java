package model;

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
}
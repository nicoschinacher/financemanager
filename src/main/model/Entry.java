package model;

public class Entry {

    private final String date;

    private final EntryAction entryAction;

    private final int number;
    private final int price;

    private final Asset asset;

    public Entry(Asset asset, String date, EntryAction entryAction, int number, int price) {
        this.date = date;
        this.entryAction = entryAction;
        this.number = number;
        this.price = price;
        this.asset = asset;
    }
    public String getDate() {
        return date;
    }
    public EntryAction getEntryAction() {
        return entryAction;
    }
    public int getNumber() {
        return number;
    }
    public int getPrice() {
        return price;
    }
    public Asset getAsset() {
        return asset;
    }
    @Override
    public String toString() {
        return getDate() + " " + getEntryAction() + " " + getNumber() + " @ " + getPrice() + "€";
    }
}
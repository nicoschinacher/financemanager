package mock;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class SampleService {

    public static List<Asset> GENERATE_SAMPLE_ASSETS() {
        List<Asset> assetList = new ArrayList<>();
        Asset assetTesla = new Asset("TSLA", "Tesla", "NASDAQ");
        assetTesla.addAssetHistory(new AssetHistory("2020-03-13", 300));
        assetTesla.addAssetHistory(new AssetHistory("2020-03-15", 320));
        assetList.add(assetTesla);
        Asset assetMSFT = new Asset("MSFT", "Microsoft", "NASDAQ");
        assetList.add(assetMSFT);
        Asset assetAMZN = new Asset("AMZN", "Amazon", "NASDAQ");
        assetList.add(assetAMZN);
        Asset assetAPPL = new Asset("APPL", "Apple", "NASDAQ");
        assetList.add(assetAPPL);
        Asset assetAMD = new Asset("AMD", "AMD", "NASDAQ");
        assetList.add(assetAMD);
        Asset assetBYND = new Asset("BYND", "Beyond", "NASDAQ");
        assetList.add(assetBYND);
        return assetList;
    }
    public static List<Entry> GENERATE_SAMPLE_PORTFOLIO(List<Asset> assetList) {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(assetList.get(0), "2020-02-16", EntryAction.BUY, 420, 69));
        entries.add(new Entry(assetList.get(0), "2020-04-12", EntryAction.BUY, 69, 420));
        return entries;
    }
}
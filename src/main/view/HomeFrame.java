package view;

import interfaces.ControllerInterface;
import model.Asset;
import model.AssetHistory;
import model.Entry;
import model.EntryAction;

import javax.swing.*;

public class HomeFrame extends HomeFrameInitializer {

    private final ControllerInterface controllerInterface;

    public HomeFrame(ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
    }
    private void updateViewAssets() {
        if(defaultListModelAssets.size() == controllerInterface.getPortfolioManager().getAssetList().size()) {
            return;
        }
        int selectedIndex = jListAssets.getSelectedIndex();
        defaultListModelAssets = new DefaultListModel<>();
        jListAssets.setModel(defaultListModelAssets);
        for(Asset asset : controllerInterface.getPortfolioManager().getAssetList()) {
            defaultListModelAssets.addElement(asset);
        }
        jListAssets.setSelectedIndex(selectedIndex);
    }
    private void updateViewAssetHistory() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolioManager().getAssetList().get(jListAssets.getSelectedIndex());
        if(defaultListModelAssetHistory.size() == asset.getAssetHistoryList().size()) {
            return;
        }
        int selectedIndex = jListAssetHistory.getSelectedIndex();
        defaultListModelAssetHistory.clear();
        for(AssetHistory assetHistory : asset.getAssetHistoryList()) {
            defaultListModelAssetHistory.addElement(assetHistory);
        }
        jListAssetHistory.setSelectedIndex(selectedIndex);
    }
    private void updateViewEntries() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolioManager().getAssetList().get(jListAssets.getSelectedIndex());
        if(defaultListModelEntries.size() == controllerInterface.getPortfolioManager().getEntryListAsset(asset).size()) {
            return;
        }
        int selectedIndex = jListEntries.getSelectedIndex();
        defaultListModelEntries.clear();
        for(Entry entry : controllerInterface.getPortfolioManager().getEntryListAsset(asset)) {
            defaultListModelEntries.addElement(entry);
        }
        jListEntries.setSelectedIndex(selectedIndex);
    }
    private void updateBaseInformation() {
        textFieldNetLiquidation.setText(
                "Value: "
                        + controllerInterface.getPortfolioManager().getNetLiquidationValue()
                        + "€"
        );
    }
    @Override
    public void updateViews() {
        updateViewAssets();
        updateViewAssetHistory();
        updateViewEntries();
        updateBaseInformation();
    }
    @Override
    protected void onAddAsset() {
        controllerInterface.getPortfolioManager().addAsset(
                new Asset(
                    jTextFieldSymbol.getText(),
                    jTextFieldCompany.getText(),
                    jTextFieldExchange.getText()
        ));
        controllerInterface.updateUI();
    }
    @Override
    protected void onAddAssetHistory() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolioManager().getAssetList().get(jListAssets.getSelectedIndex());
        asset.addAssetHistory(
                new AssetHistory(
                    jTextFieldAddHistoryDate.getText(),
                    Integer.parseInt(jTextFieldAddHistoryPrice.getText())
        ));
        controllerInterface.updateUI();
    }
    @Override
    protected void onAddEntryBuy() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolioManager().getAssetList().get(jListAssets.getSelectedIndex());
        controllerInterface.getPortfolioManager().addEntry(
                new Entry(
                        asset,
                        jTextFieldAddEntryDate.getText(),
                        EntryAction.BUY,
                        Integer.parseInt(jTextFieldAddEntryNumber.getText()),
                        Integer.parseInt(jTextFieldAddEntryPrice.getText())
                ));
        controllerInterface.updateUI();
    }
    @Override
    protected void onAddEntrySell() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolioManager().getAssetList().get(jListAssets.getSelectedIndex());
        controllerInterface.getPortfolioManager().addEntry(
                new Entry(
                        asset, jTextFieldAddEntryDate.getText(),
                        EntryAction.SELL,
                        Integer.parseInt(jTextFieldAddEntryNumber.getText()),
                        Integer.parseInt(jTextFieldAddEntryPrice.getText())
                ));
        controllerInterface.updateUI();
    }
    @Override
    protected void launchPerformanceUI() {
        controllerInterface.launchPerformanceUI();
    }
    @Override
    protected void importSQL() {
        controllerInterface.importSQL();
    }
    @Override
    protected void exportSQL() {
        controllerInterface.exportSQL();
    }
    @Override
    protected void exportExcel() {
        controllerInterface.exportExcel();
    }
}
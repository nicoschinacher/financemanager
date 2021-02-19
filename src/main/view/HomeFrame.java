package view;

import core.Config;
import interfaces.ControllerInterface;
import model.Asset;
import model.AssetHistory;
import model.Entry;
import model.EntryAction;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    private final ControllerInterface controllerInterface;

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    // List to display assets
    private DefaultListModel<Asset> defaultListModelAssets;
    private JList<Asset> jListAssets;
    private JScrollPane jScrollPaneAssets;
    // List to display asset history
    private JList<AssetHistory> jListAssetHistory;
    private DefaultListModel<AssetHistory> defaultListModelAssetHistory;
    private JScrollPane jScrollPaneAssetHistory;
    // List to display entries
    private DefaultListModel<Entry> defaultListModelEntries;
    private JList<Entry> jListEntries;
    private JScrollPane jScrollPaneEntries;
    // Views to display selected asset
    private JPanel jPanelAsset;
    private JTextField jTextFieldSymbol;
    private JTextField jTextFieldCompany;
    private JTextField jTextFieldExchange;
    // Views to display add asset history panel
    private JTextField jTextFieldAddHistoryDate;
    private JTextField jTextFieldAddHistoryPrice;
    // Views to display add entry panel
    private JTextField jTextFieldAddEntryDate;
    private JTextField jTextFieldAddEntryNumber;
    private JTextField jTextFieldAddEntryPrice;
    // Views to display base information
    private JTextField textFieldNetLiquidation;

    public HomeFrame(ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
        // Initialize view
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setResizable(false);
        initializeView();
        updateViews();
        setVisible(true);
    }
    public void updateViews() {
        updateViewAssets();
        updateViewAssetHistory();
        updateViewEntries();
        updateBaseInformation();
    }
    private void updateViewAssets() {
        if(defaultListModelAssets.size() == controllerInterface.getPortfolio().getAssetList().size()) {
            return;
        }
        int selectedIndex = jListAssets.getSelectedIndex();
        defaultListModelAssets.clear();
        for(Asset asset : controllerInterface.getPortfolio().getAssetList()) {
            defaultListModelAssets.addElement(asset);
        }
        jListAssets.setSelectedIndex(selectedIndex);
    }
    private void updateViewAssetHistory() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolio().getAssetList().get(jListAssets.getSelectedIndex());
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
        Asset asset = controllerInterface.getPortfolio().getAssetList().get(jListAssets.getSelectedIndex());
        if(defaultListModelEntries.size() == controllerInterface.getPortfolio().getEntryList(asset).size()) {
            return;
        }
        int selectedIndex = jListEntries.getSelectedIndex();
        defaultListModelEntries.clear();
        for(Entry entry : controllerInterface.getPortfolio().getEntryList(asset)) {
            defaultListModelEntries.addElement(entry);
        }
        jListEntries.setSelectedIndex(selectedIndex);
    }
    private void updateBaseInformation() {
        textFieldNetLiquidation.setText(
                "Value: "
                + controllerInterface.getPortfolio().getNetLiquidationValue()
                + "€"
        );
    }
    private void initializeView() {
        initializeAssetList();
        initializeAssetHistoryList();
        initializeEntriesList();
        initializeAssetPanel();
        initializeAddAssetHistoryPanel();
        initializeAddEntryPanel();
        initializeBasePanel();
    }
    private void initializeAssetList() {
        // Create components
        defaultListModelAssets = new DefaultListModel<>();
        jListAssets = new JList<>(defaultListModelAssets);
        jListAssets.addListSelectionListener(e -> updateViewEntries());
        jScrollPaneAssets = new JScrollPane(jListAssets);
        jScrollPaneAssets.setPreferredSize(
                new Dimension(
                        WIDTH / 3,
                        HEIGHT / 3
        ));
        jScrollPaneAssets.setBounds(
                0,
                0,
                WIDTH / 3,
                HEIGHT / 3
        );
        add(jScrollPaneAssets);
    }
    private void initializeAssetHistoryList() {
        defaultListModelAssetHistory = new DefaultListModel<>();
        jListAssetHistory = new JList<>(defaultListModelAssetHistory);
        jScrollPaneAssetHistory = new JScrollPane(jListAssetHistory);
        jScrollPaneAssetHistory.setPreferredSize(
                new Dimension(
                        WIDTH / 3,
                        HEIGHT / 3
        ));
        jScrollPaneAssetHistory.setBounds(
                jScrollPaneAssets.getX() + jScrollPaneAssets.getWidth(),
                jScrollPaneAssets.getY(),
                WIDTH / 3,
                HEIGHT / 3
        );
        add(jScrollPaneAssetHistory);
    }
    private void initializeEntriesList() {
        defaultListModelEntries = new DefaultListModel<>();
        jListEntries = new JList<>(defaultListModelEntries);
        jScrollPaneEntries = new JScrollPane(jListEntries);
        jScrollPaneEntries.setPreferredSize(
                new Dimension(
                    WIDTH / 3,
                    HEIGHT / 3
        ));
        jScrollPaneEntries.setBounds(
                jScrollPaneAssetHistory.getX() + jScrollPaneAssetHistory.getWidth(),
                jScrollPaneAssetHistory.getY(),
                WIDTH / 3,
                HEIGHT / 3
        );
        add(jScrollPaneEntries);
    }
    private void initializeAssetPanel() {
        jPanelAsset = new JPanel();
        jPanelAsset.setBounds(0,
                jScrollPaneAssets.getY() + jScrollPaneAssets.getHeight(),
                WIDTH / 3,
                HEIGHT / 3
        );
        jPanelAsset.setLayout(null);
        add(jPanelAsset);

        JLabel jLabelSymbol = new JLabel("Symbol");
        jLabelSymbol.setBounds(
                Config.PADDING,
                Config.PADDING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAsset.add(jLabelSymbol);

        jTextFieldSymbol = new JTextField();
        jTextFieldSymbol.setBounds(
                jLabelSymbol.getX() + jLabelSymbol.getWidth() + Config.SPACING,
                Config.PADDING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jTextFieldSymbol.setEditable(true);
        jPanelAsset.add(jTextFieldSymbol);

        JLabel jLabelCompany = new JLabel("Company");
        jLabelCompany.setBounds(
                Config.PADDING,
                jTextFieldSymbol.getY() + jTextFieldSymbol.getHeight() + Config.SPACING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAsset.add(jLabelCompany);

        jTextFieldCompany = new JTextField();
        jTextFieldCompany.setBounds(
                jLabelCompany.getX() + jLabelCompany.getWidth() + Config.SPACING,
                jLabelCompany.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jTextFieldCompany.setEditable(true);
        jPanelAsset.add(jTextFieldCompany);

        JLabel jLabelExchange = new JLabel("Exchange");
        jLabelExchange.setBounds(
                Config.PADDING,
                jTextFieldCompany.getY() + jTextFieldCompany.getHeight() + Config.SPACING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT);
        jPanelAsset.add(jLabelExchange);

        jTextFieldExchange = new JTextField();
        jTextFieldExchange.setBounds(
                jLabelExchange.getX() + jLabelExchange.getWidth() + Config.SPACING,
                jTextFieldCompany.getY() + jTextFieldCompany.getHeight() + Config.SPACING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jTextFieldExchange.setEditable(true);
        jPanelAsset.add(jTextFieldExchange);

        JButton jButtonAddAsset = new JButton("Add");
        jButtonAddAsset.setBounds(
                jTextFieldExchange.getX(),
                jTextFieldExchange.getY() + jTextFieldExchange.getHeight() + Config.SPACING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonAddAsset.addActionListener(e -> onAddAsset());
        jPanelAsset.add(jButtonAddAsset);
    }
    private void initializeAddAssetHistoryPanel() {
        JPanel jPanelAddAssetHistory = new JPanel();
        jPanelAddAssetHistory.setBounds(
                jScrollPaneAssetHistory.getX(),
                jScrollPaneAssetHistory.getY() + jScrollPaneAssetHistory.getHeight(),
                WIDTH / 3,
                HEIGHT / 3
        );
        jPanelAddAssetHistory.setLayout(null);
        add(jPanelAddAssetHistory);

        JLabel jLabelDate = new JLabel("Date");
        jLabelDate.setBounds(
                Config.PADDING,
                Config.PADDING,
                Config.LABEL_WIDTH,
                Config.LABEL_HEIGHT
        );
        jPanelAddAssetHistory.add(jLabelDate);

        jTextFieldAddHistoryDate = new JTextField();
        jTextFieldAddHistoryDate.setBounds(
                jLabelDate.getX() + jLabelDate.getWidth() + Config.SPACING,
                jLabelDate.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAddAssetHistory.add(jTextFieldAddHistoryDate);

        JLabel jLabelPrice = new JLabel("Price");
        jLabelPrice.setBounds(
                jLabelDate.getX(),
                jTextFieldAddHistoryDate.getY() + jTextFieldAddHistoryDate.getHeight() + Config.SPACING,
                Config.LABEL_WIDTH,
                Config.LABEL_HEIGHT
        );
        jPanelAddAssetHistory.add(jLabelPrice);

        jTextFieldAddHistoryPrice = new JTextField();
        jTextFieldAddHistoryPrice.setBounds(
                jLabelPrice.getX() + jLabelPrice.getWidth() + Config.SPACING,
                jLabelPrice.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAddAssetHistory.add(jTextFieldAddHistoryPrice);

        JButton jButtonAdd = new JButton("Add");
        jButtonAdd.setBounds(
                jTextFieldAddHistoryPrice.getX(),
                jTextFieldAddHistoryPrice.getY() + jTextFieldAddHistoryPrice.getHeight() + Config.SPACING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonAdd.addActionListener(e -> onAddAssetHistory());
        jPanelAddAssetHistory.add(jButtonAdd);
    }
    private void initializeAddEntryPanel() {
        // Views to display add entry panel
        JPanel jPanelAddEntry = new JPanel();
        jPanelAddEntry.setBounds(
                jScrollPaneEntries.getX(),
                jScrollPaneEntries.getY() + jScrollPaneEntries.getHeight(),
                WIDTH / 3,
                HEIGHT / 3
        );
        jPanelAddEntry.setLayout(null);
        add(jPanelAddEntry);

        JLabel jLabelDate = new JLabel("Date");
        jLabelDate.setBounds(
                Config.PADDING,
                Config.PADDING,
                Config.LABEL_WIDTH,
                Config.LABEL_HEIGHT
        );
        jPanelAddEntry.add(jLabelDate);

        jTextFieldAddEntryDate = new JTextField();
        jTextFieldAddEntryDate.setBounds(
                jLabelDate.getX() + jLabelDate.getWidth() + Config.SPACING,
                jLabelDate.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAddEntry.add(jTextFieldAddEntryDate);

        JLabel jLabelNumber = new JLabel("Number");
        jLabelNumber.setBounds(
                jLabelDate.getX(),
                jTextFieldAddEntryDate.getY() + jTextFieldAddEntryDate.getHeight() + Config.SPACING,
                Config.LABEL_WIDTH,
                Config.LABEL_HEIGHT
        );
        jPanelAddEntry.add(jLabelNumber);

        jTextFieldAddEntryNumber = new JTextField();
        jTextFieldAddEntryNumber.setBounds(
                jLabelNumber.getX() + jLabelNumber.getWidth() + Config.SPACING,
                jLabelNumber.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAddEntry.add(jTextFieldAddEntryNumber);

        JLabel jLabelPrice = new JLabel("Price");
        jLabelPrice.setBounds(
                jLabelNumber.getX(),
                jTextFieldAddEntryNumber.getY() + jTextFieldAddEntryNumber.getHeight() + Config.SPACING,
                Config.LABEL_WIDTH,
                Config.LABEL_HEIGHT
        );
        jPanelAddEntry.add(jLabelPrice);

        jTextFieldAddEntryPrice = new JTextField();
        jTextFieldAddEntryPrice.setBounds(
                jLabelPrice.getX() + jLabelPrice.getWidth() + Config.SPACING,
                jLabelPrice.getY(),
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        jPanelAddEntry.add(jTextFieldAddEntryPrice);

        JButton jButtonAdd = new JButton("BUY");
        jButtonAdd.setBounds(
                jTextFieldAddEntryPrice.getX(),
                jTextFieldAddEntryPrice.getY() + jTextFieldAddEntryPrice.getHeight() + Config.SPACING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonAdd.addActionListener(e -> onAddEntryBuy());
        jPanelAddEntry.add(jButtonAdd);

        JButton jButtonSell = new JButton("SELL");
        jButtonSell.setBounds(
                jButtonAdd.getX(),
                jButtonAdd.getY() + jButtonAdd.getHeight() + Config.SPACING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonSell.addActionListener(e -> onAddEntrySell());
        jPanelAddEntry.add(jButtonSell);
    }
    private void initializeBasePanel() {
        JPanel jPanelBase = new JPanel();
        jPanelBase.setBounds(
                0,
                jPanelAsset.getY() + jPanelAsset.getHeight() + Config.SPACING,
                WIDTH,
                HEIGHT / 3
        );
        jPanelBase.setLayout(null);
        add(jPanelBase);

        textFieldNetLiquidation = new JTextField();
        textFieldNetLiquidation.setBounds(
                Config.PADDING,
                Config.PADDING,
                Config.TEXTFIELD_WIDTH,
                Config.TEXTFIELD_HEIGHT
        );
        textFieldNetLiquidation.setEditable(false);
        jPanelBase.add(textFieldNetLiquidation);

        JButton jButtonPerformance = new JButton("Performance");
        jButtonPerformance.setBounds(
                textFieldNetLiquidation.getX() + textFieldNetLiquidation.getWidth() + Config.SPACING,
                Config.PADDING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonPerformance.addActionListener(e -> controllerInterface.launchPerformanceUI());
        jPanelBase.add(jButtonPerformance);

        JButton jButtonImportSQL = new JButton("Import SQL");
        jButtonImportSQL.setBounds(
                jButtonPerformance.getX() + jButtonPerformance.getWidth() + Config.SPACING,
                Config.PADDING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonImportSQL.addActionListener(e -> controllerInterface.importSQL());
        jPanelBase.add(jButtonImportSQL);

        JButton jButtonExportSQL = new JButton("Export SQL");
        jButtonExportSQL.setBounds(
                jButtonImportSQL.getX() + jButtonImportSQL.getWidth() + Config.SPACING,
                Config.PADDING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonExportSQL.addActionListener(e -> controllerInterface.exportSQL());
        jPanelBase.add(jButtonExportSQL);

        JButton jButtonExportExcel = new JButton("Export Excel");
        jButtonExportExcel.setBounds(
                jButtonExportSQL.getX() + jButtonExportSQL.getWidth() + Config.SPACING,
                Config.PADDING,
                Config.BUTTON_WIDTH,
                Config.BUTTON_HEIGHT
        );
        jButtonExportExcel.addActionListener(e -> controllerInterface.exportExcel());
        jPanelBase.add(jButtonExportExcel);
    }
    private void onAddAsset() {
        controllerInterface.getPortfolio().addAsset(
                new Asset(
                    jTextFieldSymbol.getText(),
                    jTextFieldCompany.getText(),
                    jTextFieldExchange.getText()
        ));
        controllerInterface.updateUI();
    }
    private void onAddAssetHistory() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolio().getAssetList().get(jListAssets.getSelectedIndex());
        asset.addAssetHistory(
                new AssetHistory(
                    jTextFieldAddHistoryDate.getText(),
                    Integer.parseInt(jTextFieldAddHistoryPrice.getText())
        ));
        controllerInterface.updateUI();
    }
    private void onAddEntryBuy() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolio().getAssetList().get(jListAssets.getSelectedIndex());
        controllerInterface.getPortfolio().addEntry(
                new Entry(
                        asset,
                        jTextFieldAddEntryDate.getText(),
                        EntryAction.BUY,
                        Integer.parseInt(jTextFieldAddEntryNumber.getText()),
                        Integer.parseInt(jTextFieldAddEntryPrice.getText())
                ));
        controllerInterface.updateUI();
    }
    private void onAddEntrySell() {
        if(jListAssets.getSelectedIndex() == -1) {
            return;
        }
        Asset asset = controllerInterface.getPortfolio().getAssetList().get(jListAssets.getSelectedIndex());
        controllerInterface.getPortfolio().addEntry(
                new Entry(
                        asset, jTextFieldAddEntryDate.getText(),
                        EntryAction.SELL,
                        Integer.parseInt(jTextFieldAddEntryNumber.getText()),
                        Integer.parseInt(jTextFieldAddEntryPrice.getText())
                ));
        controllerInterface.updateUI();
    }
}
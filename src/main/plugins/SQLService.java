package plugins;

import controller.PortfolioManager;
import model.*;

import java.sql.*;

public class SQLService {

    private static Connection connection;

    private static final String CREATE_TABLE_ASSET = "CREATE TABLE ASSET(SYMBOL VARCHAR, COMPANY VARCHAR, EXCHANGE VARCHAR, PRIMARY KEY(SYMBOL));";
    private static final String CREATE_TABLE_ASSET_HISTORY = "CREATE TABLE ASSET_HISTORY(SYMBOL VARCHAR, DATE VARCHAR, PRICE INT, FOREIGN KEY(SYMBOL) REFERENCES ASSET(SYMBOL));";
    private static final String CREATE_TABLE_ENTRY = "CREATE TABLE ENTRY(SYMBOL VARCHAR, DATE VARCHAR, ACTION VARCHAR, NUMBER INT, PRICE INT, FOREIGN KEY(SYMBOL) REFERENCES ASSET(SYMBOL));";

    private static final String READ_TABLE_ASSET = "SELECT SYMBOL, COMPANY, EXCHANGE from ASSET";
    private static final String READ_TABLE_ASSET_HISTORY = "SELECT ASSET.SYMBOL, DATE, PRICE from ASSET, ASSET_HISTORY WHERE ASSET.SYMBOL = ASSET_HISTORY.SYMBOL;";
    private static final String READ_TABLE_ENTRY = "SELECT ASSET.SYMBOL, COMPANY, EXCHANGE, DATE, ACTION, NUMBER, PRICE from ASSET, ENTRY WHERE ASSET.SYMBOL = ENTRY.SYMBOL;";

    private static final String INSERT_TABLE_ASSET = "INSERT INTO ASSET VALUES (?, ?, ?);";
    private static final String INSERT_TABLE_ASSET_HISTORY = "INSERT INTO ASSET_HISTORY VALUES (?, ?, ?);";
    private static final String INSERT_TABLE_ENTRY = "INSERT INTO ENTRY VALUES (?, ?, ?, ?, ?);";

    public static PortfolioManager importSQL() {
        initializeConnection();
        return getTables();
    }
    public static void exportSQL(PortfolioManager portfolioManager) {
        initializeConnection();
        createTables();
        insertTables(portfolioManager);
    }
    private static void initializeConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:financemanager.db");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static PortfolioManager getTables() {
        PortfolioManager portfolioManager = new PortfolioManager();
        readTableAsset(portfolioManager);
        readTableAssetHistory(portfolioManager);
        readTableEntry(portfolioManager);
        return portfolioManager;
    }
    private static void readTableAsset(PortfolioManager portfolioManager) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(READ_TABLE_ASSET);
            while (resultSet.next()) {
                portfolioManager.addAsset(
                        new Asset(
                            resultSet.getString("SYMBOL"),
                            resultSet.getString("COMPANY"),
                            resultSet.getString("EXCHANGE")
                ));
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void readTableAssetHistory(PortfolioManager portfolioManager) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(READ_TABLE_ASSET_HISTORY);
            while (resultSet.next()) {
                Asset asset = portfolioManager.getAsset(resultSet.getString("SYMBOL"));
                asset.addAssetHistory(
                        new AssetHistory(
                                resultSet.getString("DATE"),
                                resultSet.getInt("PRICE")
                        )
                );
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void readTableEntry(PortfolioManager portfolioManager) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(READ_TABLE_ENTRY);
            while (resultSet.next()) {
                Asset asset = portfolioManager.getAsset(resultSet.getString("SYMBOL"));
                EntryAction entryAction = EntryAction.SELL;
                if(resultSet.getString("ACTION").equals("BUY")) {
                    entryAction = EntryAction.BUY;
                }
                portfolioManager.addEntry(
                        new Entry(
                                asset,
                                resultSet.getString("DATE"),
                                entryAction,
                                resultSet.getInt("NUMBER"),
                                resultSet.getInt("PRICE")
                ));
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void createTables() {
        createTableAsset();
        createTableAssetHistory();
        createTableEntry();
    }
    private static void insertTables(PortfolioManager portfolioManager) {
        insertTableAsset(portfolioManager);
        insertTableAssetHistory(portfolioManager);
        insertTableEntry(portfolioManager);
    }
    private static void createTableAsset() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ASSET;");
            statement.executeUpdate(CREATE_TABLE_ASSET);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void createTableAssetHistory() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ASSET_HISTORY;");
            statement.executeUpdate(CREATE_TABLE_ASSET_HISTORY);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void createTableEntry() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ENTRY;");
            statement.executeUpdate(CREATE_TABLE_ENTRY);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void insertTableAsset(PortfolioManager portfolioManager) {
        try {
            for(Asset asset : portfolioManager.getAssetList()) {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_TABLE_ASSET);
                prepareStatement.setString(1, asset.getSymbol());
                prepareStatement.setString(2, asset.getCompany());
                prepareStatement.setString(3, asset.getExchange());
                prepareStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void insertTableAssetHistory(PortfolioManager portfolioManager) {
        try {
            for(Asset asset : portfolioManager.getAssetList()) {
                for (AssetHistory assetHistory : asset.getAssetHistoryList()) {
                    PreparedStatement prepareStatement = connection.prepareStatement(INSERT_TABLE_ASSET_HISTORY);
                    prepareStatement.setString(1, asset.getSymbol());
                    prepareStatement.setString(2, assetHistory.getDate());
                    prepareStatement.setInt(3, assetHistory.getPrice());
                    prepareStatement.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void insertTableEntry(PortfolioManager portfolioManager) {
        try {
            for(Asset asset : portfolioManager.getAssetList()) {
                for (Entry entry : portfolioManager.getEntryListAsset(asset)) {
                    PreparedStatement prepareStatement = connection.prepareStatement(INSERT_TABLE_ENTRY);
                    prepareStatement.setString(1, asset.getSymbol());
                    prepareStatement.setString(2, entry.getDate());
                    prepareStatement.setString(3, entry.getEntryAction() + "");
                    prepareStatement.setInt(4, entry.getNumber());
                    prepareStatement.setInt(5, entry.getPrice());
                    prepareStatement.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
package controller;

import model.*;

import java.sql.*;

public class SQLService {

    private static Connection connection;

    public static Portfolio importSQL() {
        initializeConnection();
        return getTables();
    }
    public static void exportSQL(Portfolio portfolio) {
        initializeConnection();
        createTables();
        insertTables(portfolio);
    }
    private static void initializeConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:financemanager.db");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static Portfolio getTables() {
        Portfolio portfolio = new Portfolio();
        readTableAsset(portfolio);
        readTableAssetHistory(portfolio);
        readTableEntry(portfolio);
        return portfolio;
    }
    private static void readTableAsset(Portfolio portfolio) {
        String query = "SELECT SYMBOL, COMPANY, EXCHANGE from ASSET";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                portfolio.addAsset(
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
    private static void readTableAssetHistory(Portfolio portfolio) {
        String query = "SELECT ASSET.SYMBOL, DATE, PRICE from ASSET, ASSET_HISTORY " +
                "WHERE ASSET.SYMBOL = ASSET_HISTORY.SYMBOL;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Asset asset = portfolio.getAsset(resultSet.getString("SYMBOL"));
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
    private static void readTableEntry(Portfolio portfolio) {
        String query = "SELECT ASSET.SYMBOL, COMPANY, EXCHANGE, DATE, ACTION, NUMBER, PRICE from ASSET, ENTRY " +
                "WHERE ASSET.SYMBOL = ENTRY.SYMBOL;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Asset asset = portfolio.getAsset(resultSet.getString("SYMBOL"));
                EntryAction entryAction = EntryAction.SELL;
                if(resultSet.getString("ACTION").equals("BUY")) {
                    entryAction = EntryAction.BUY;
                }
                portfolio.addEntry(
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
    private static void insertTables(Portfolio portfolio) {
        insertTableAsset(portfolio);
        insertTableAssetHistory(portfolio);
        insertTableEntry(portfolio);
    }
    private static void createTableAsset() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ASSET;");
            statement.executeUpdate("CREATE TABLE ASSET(SYMBOL VARCHAR, COMPANY VARCHAR, EXCHANGE VARCHAR, PRIMARY KEY(SYMBOL));");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void createTableAssetHistory() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ASSET_HISTORY;");
            statement.executeUpdate("CREATE TABLE ASSET_HISTORY(SYMBOL VARCHAR, DATE VARCHAR, PRICE INT, FOREIGN KEY(SYMBOL) REFERENCES ASSET(SYMBOL));");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void createTableEntry() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS ENTRY;");
            statement.executeUpdate("CREATE TABLE ENTRY(SYMBOL VARCHAR, DATE VARCHAR, ACTION VARCHAR, NUMBER INT, PRICE INT, FOREIGN KEY(SYMBOL) REFERENCES ASSET(SYMBOL));");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void insertTableAsset(Portfolio portfolio) {
        try {
            for(Asset asset : portfolio.getAssetList()) {
                PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO ASSET VALUES (?, ?, ?);");
                prepareStatement.setString(1, asset.getSymbol());
                prepareStatement.setString(2, asset.getCompany());
                prepareStatement.setString(3, asset.getExchange());
                prepareStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
    private static void insertTableAssetHistory(Portfolio portfolio) {
        try {
            for(Asset asset : portfolio.getAssetList()) {
                for (AssetHistory assetHistory : asset.getAssetHistoryList()) {
                    PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO ASSET_HISTORY VALUES (?, ?, ?);");
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
    private static void insertTableEntry(Portfolio portfolio) {
        try {
            for(Asset asset : portfolio.getAssetList()) {
                for (Entry entry : portfolio.getEntryList(asset)) {
                    PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO ENTRY VALUES (?, ?, ?, ?, ?);");
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
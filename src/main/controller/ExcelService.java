package controller;

import model.Asset;
import model.AssetHistory;
import model.Entry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelService {

    private final PortfolioManager portfolioManager;

    public ExcelService(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }
    public void createExcel() {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet("Financemanager");

        int rowNumber = 0;
        Row rowAssetHeader = sheet.createRow(rowNumber++);
        int cellNumber = 0;

        Cell cellHeaderAssetSymbol = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetSymbol.setCellValue("Symbol");
        Cell cellHeaderAssetCompany = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetCompany.setCellValue("Company");
        Cell cellHeaderAssetExchange = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetExchange.setCellValue("Exchange");

        cellNumber++;
        Cell cellHeaderAssetHistorySymbol = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetHistorySymbol.setCellValue("Symbol");
        Cell cellHeaderAssetHistoryDate = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetHistoryDate.setCellValue("Date");
        Cell cellHeaderAssetHistoryPrice = rowAssetHeader.createCell(cellNumber++);
        cellHeaderAssetHistoryPrice.setCellValue("Price");

        cellNumber++;
        Cell cellHeaderEntrySymbol = rowAssetHeader.createCell(cellNumber++);
        cellHeaderEntrySymbol.setCellValue("Symbol");
        Cell cellHeaderEntryDate = rowAssetHeader.createCell(cellNumber++);
        cellHeaderEntryDate.setCellValue("Date");
        Cell cellHeaderEntryAction = rowAssetHeader.createCell(cellNumber++);
        cellHeaderEntryAction.setCellValue("Action");
        Cell cellHeaderEntryNumber = rowAssetHeader.createCell(cellNumber++);
        cellHeaderEntryNumber.setCellValue("Number");
        Cell cellHeaderEntryPrice = rowAssetHeader.createCell(cellNumber);
        cellHeaderEntryPrice.setCellValue("Price");

        int rowAssetNumber = rowNumber;
        int rowNumberAssetHistory = rowNumber;
        int rowNumberEntry = rowNumber;
        for (Asset asset : portfolioManager.getAssetList()) {
            // Write asset
            Row rowAsset = sheet.getRow(rowAssetNumber);
            if(rowAsset == null) {
                rowAsset = sheet.createRow(rowAssetNumber);
            }
            Cell cellAssetSymbol = rowAsset.createCell(cellHeaderAssetSymbol.getColumnIndex());
            cellAssetSymbol.setCellValue(asset.getSymbol());
            Cell cellAssetCompany = rowAsset.createCell(cellHeaderAssetCompany.getColumnIndex());
            cellAssetCompany.setCellValue(asset.getCompany());
            Cell cellAssetExchange = rowAsset.createCell(cellHeaderAssetExchange.getColumnIndex());
            cellAssetExchange.setCellValue(asset.getExchange());
            rowAssetNumber++;
            // Write asset histories
            for (AssetHistory assetHistory : asset.getAssetHistoryList()) {
                Row rowAssetHistory = sheet.getRow(rowNumberAssetHistory);
                if(rowAssetHistory == null) {
                    rowAssetHistory = sheet.createRow(rowNumberAssetHistory);
                }
                Cell cellAssetHistorySymbol = rowAssetHistory.createCell(cellHeaderAssetHistorySymbol.getColumnIndex());
                cellAssetHistorySymbol.setCellValue(asset.getSymbol());
                Cell cellAssetHistoryDate = rowAssetHistory.createCell(cellHeaderAssetHistoryDate.getColumnIndex());
                cellAssetHistoryDate.setCellValue(assetHistory.getDate());
                Cell cellAssetHistoryPrice = rowAssetHistory.createCell(cellHeaderAssetHistoryPrice.getColumnIndex());
                cellAssetHistoryPrice.setCellValue(assetHistory.getPrice());
                rowNumberAssetHistory++;
            }
            // Write asset entries
            for(Entry entry : portfolioManager.getEntryListAsset(asset)) {
                Row rowEntry = sheet.getRow(rowNumberEntry);
                if (rowEntry == null) {
                    rowEntry = sheet.createRow(rowNumberEntry);
                }
                Cell cellEntrySymbol = rowEntry.createCell(cellHeaderEntrySymbol.getColumnIndex());
                cellEntrySymbol.setCellValue(asset.getSymbol());
                Cell cellEntryDate = rowEntry.createCell(cellHeaderEntryDate.getColumnIndex());
                cellEntryDate.setCellValue(entry.getDate());
                Cell cellEntryAction = rowEntry.createCell(cellHeaderEntryAction.getColumnIndex());
                cellEntryAction.setCellValue(entry.getEntryAction() + "");
                Cell cellEntryNumber = rowEntry.createCell(cellHeaderEntryNumber.getColumnIndex());
                cellEntryNumber.setCellValue(entry.getNumber());
                Cell cellEntryPrice = rowEntry.createCell(cellHeaderEntryPrice.getColumnIndex());
                cellEntryPrice.setCellValue(entry.getPrice());
                rowNumberEntry++;
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("Financemanager.xlsx");
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
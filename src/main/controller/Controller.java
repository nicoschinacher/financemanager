package controller;

import interfaces.ControllerInterface;
import mock.SampleService;
import model.Portfolio;
import view.HomeFrame;
import view.PerfomanceFrame;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private final ControllerInterface controllerInterface;

    private final HomeFrame homeFrame;

    private Portfolio portfolio;

    public Controller() {
        this.portfolio = new Portfolio();
        this.portfolio.addAllAsset(SampleService.GENERATE_SAMPLE_ASSETS());
        this.portfolio.addAllEntry(SampleService.GENERATE_SAMPLE_PORTFOLIO(portfolio.getAssetList()));
        this.controllerInterface = initializeInterface();
        // Initialize UI
        this.homeFrame = new HomeFrame(controllerInterface);
        this.setupScheduledTask();
    }
    private ControllerInterface initializeInterface() {
        return new ControllerInterface() {
            @Override
            public void launchPerformanceUI() {
                new PerfomanceFrame(controllerInterface);
            }
            @Override
            public void updateUI() {
                if(homeFrame != null) {
                    homeFrame.updateViews();
                }
            }
            @Override
            public Portfolio getPortfolio() {
                return portfolio;
            }
            @Override
            public void importSQL() {
                portfolio = SQLService.importSQL();
                updateUI();
            }
            @Override
            public void exportSQL() {
                SQLService.exportSQL(portfolio);
            }
            @Override
            public void exportExcel() {
                ExcelService excelService = new ExcelService(portfolio);
                excelService.createExcel();
            }
        };
    }
    private void setupScheduledTask() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                controllerInterface.updateUI();
            }
        }, 32, 32);
    }
}
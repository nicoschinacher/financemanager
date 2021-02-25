package controller;

import interfaces.ControllerInterface;
import view.HomeFrame;
import view.PerfomanceFrame;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private final ControllerInterface controllerInterface;

    private final HomeFrame homeFrame;

    private PortfolioManager portfolioManager;

    public Controller() {
        this.portfolioManager = PortfolioManagerFactory.createPortfolioManager();
        this.controllerInterface = initializeInterface();
        // Initialize UI
        this.homeFrame = new HomeFrame(controllerInterface);
        this.setupScheduledTask();
    }
    private ControllerInterface initializeInterface() {
        return new ControllerInterface() {
            @Override
            public void launchPerformanceUI() {
                new PerfomanceFrame(portfolioManager.getPerformanceList());
            }
            @Override
            public void updateUI() {
                if(homeFrame != null) {
                    homeFrame.updateViews();
                }
            }
            @Override
            public PortfolioManager getPortfolioManager() {
                return portfolioManager;
            }
            @Override
            public void importSQL() {
                portfolioManager = SQLService.importSQL();
                updateUI();
            }
            @Override
            public void exportSQL() {
                SQLService.exportSQL(portfolioManager);
            }
            @Override
            public void exportExcel() {
                ExcelService excelService = new ExcelService(portfolioManager);
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
package interfaces;

import controller.PortfolioManager;

public interface ControllerInterface {

    void launchPerformanceUI();
    void updateUI();

    PortfolioManager getPortfolioManager();

    void importSQL();
    void exportSQL();
    void exportExcel();
}
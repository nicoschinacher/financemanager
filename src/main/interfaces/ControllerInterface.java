package interfaces;

import model.Portfolio;

public interface ControllerInterface {

    void launchPerformanceUI();
    void updateUI();

    Portfolio getPortfolio();

    void importSQL();
    void exportSQL();
    void exportExcel();
}
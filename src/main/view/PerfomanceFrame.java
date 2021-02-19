package view;

import core.Config;
import interfaces.ControllerInterface;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class PerfomanceFrame extends JFrame {

    private final ControllerInterface controllerInterface;

    private final int WIDTH = 1200;
    private final int HEIGHT = 600;

    private final int BASE_PANEL_HEIGHT = 100;

    private JPanel jPanelGraph;

    private JTextField textFieldNetLiquidation;

    public PerfomanceFrame(ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
        // Initialize view
        setTitle("Performance");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setResizable(false);
        initializeView();
        updateViews();
        setVisible(true);
    }
    public void updateViews() {
        updateBaseInformation();
    }
    private void updateBaseInformation() {
        textFieldNetLiquidation.setText(
                "Value: "
                        + controllerInterface.getPortfolio().getNetLiquidationValue()
                        + "€"
        );
    }
    private void initializeView() {
        initializeGraphPanel();
        initializeBasePanel();
    }
    private void initializeGraphPanel() {
        jPanelGraph = new JPanel();
        jPanelGraph.setLayout(null);
        jPanelGraph.setBackground(Color.WHITE);
        jPanelGraph.setBounds(
                0,
                0,
                WIDTH,
                HEIGHT - BASE_PANEL_HEIGHT
        );
        JPanel jPanelSub = new JPanel();
        jPanelSub.setLayout(new java.awt.BorderLayout());
        jPanelSub.setBounds(
                0,
                0,
                WIDTH - 100,
                HEIGHT - BASE_PANEL_HEIGHT
        );
        jPanelGraph.add(jPanelSub);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int x = 0;
        for(Integer integer : controllerInterface.getPortfolio().getPerformanceList()) {
            dataset.setValue(integer, "Tendies", "" + x++);
        }
        JFreeChart jFreeChart = ChartFactory.createLineChart(
                "Performance",
                "Time",
                "Value",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        jPanelSub.add(chartPanel, BorderLayout.CENTER);
        add(jPanelGraph);
    }
    private void initializeBasePanel() {
        JPanel jPanelBase = new JPanel();
        jPanelBase.setBounds(
                jPanelGraph.getX(),
                jPanelGraph.getY() + jPanelGraph.getHeight(),
                WIDTH,
                BASE_PANEL_HEIGHT
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
    }
}
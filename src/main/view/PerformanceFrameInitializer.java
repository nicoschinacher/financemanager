package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

abstract class PerformanceFrameInitializer extends JFrame {

    private final int WIDTH = 1200;
    private final int HEIGHT = 600;

    protected DefaultCategoryDataset dataset;

    protected PerformanceFrameInitializer() {
        // Initialize view
        setTitle("Performance");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setResizable(false);
        initializeView();
        setVisible(true);
    }
    private void initializeView() {
        initializeGraphPanel();
    }
    private void initializeGraphPanel() {
        JPanel jPanelGraph = new JPanel();
        jPanelGraph.setLayout(null);
        jPanelGraph.setBackground(Color.WHITE);
        jPanelGraph.setBounds(
                0,
                0,
                WIDTH,
                HEIGHT
        );
        JPanel jPanelSub = new JPanel();
        jPanelSub.setLayout(new java.awt.BorderLayout());
        jPanelSub.setBounds(
                0,
                0,
                WIDTH - 100,
                HEIGHT
        );
        jPanelGraph.add(jPanelSub);
        dataset = new DefaultCategoryDataset();
        JFreeChart jFreeChart = ChartFactory.createLineChart(
                "Performance",
                "Time",
                "Value",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.invalidate();
        jPanelSub.add(chartPanel, BorderLayout.CENTER);
        add(jPanelGraph);
    }
    protected abstract void updateGraph(List<Integer> performance);
}
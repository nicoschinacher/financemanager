package view;

import core.Config;

import javax.swing.*;

abstract class AlertFrameInitializer extends JFrame {

    private final int WIDTH = 400;
    private final int HEIGHT = 100;

    protected JTextField jTextField;

    protected AlertFrameInitializer() {
        // Initialize view
        setTitle("Alert");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setResizable(false);
        initializeView();
        setVisible(true);
    }
    private void initializeView() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(0, 0, WIDTH, HEIGHT);
        jTextField = new JTextField();
        jTextField.setBounds(
                Config.PADDING,
                Config.PADDING,
                WIDTH - Config.PADDING * 3,
                HEIGHT - Config.PADDING * 4
        );
        jTextField.setEditable(false);
        jPanel.add(jTextField);
        add(jPanel);
    }
}
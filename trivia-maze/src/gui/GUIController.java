package gui;

import javax.swing.*;
import java.awt.*;

public class GUIController {
    private JFrame frame;
    GridBagConstraints gbc = new GridBagConstraints();

    public GUIController() {
        createGUI();
    }

    public void resetConstraints() {
        // Set Default Constraints
        // Grid Bag Reference: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 15;
    }

    private void createGUI() {
        frame = new JFrame("Trivia Maze");
        frame.setSize(1280, 720);
        frame.setLayout(new GridBagLayout());

        resetConstraints();

        JButton start = new JButton("Start");
        start.addActionListener(action -> {
            frame.setContentPane(new GamePanel());
            refresh();
        });
        frame.add(start, gbc);

        JButton exit = new JButton("Exit");
        exit.addActionListener(action -> frame.dispose());
        gbc.gridy++;
        frame.add(exit, gbc);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void refresh() {
        frame.revalidate();
        frame.repaint();
    }
}

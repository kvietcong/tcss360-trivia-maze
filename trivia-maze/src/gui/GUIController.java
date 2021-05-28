package gui;

import javax.swing.*;
import java.awt.*;

public class GUIController {
    private JPanel mainPanel;

    public GUIController() {
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Trivia Maze");
        frame.setSize(1280, 720);
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        mainPanel.add(new MainMenuPanel(frame::dispose, this::showCard), "MAIN");
        mainPanel.add(new GamePanel(this::showCard), "GAME");

        showCard("MAIN");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void showCard(String card) { ((CardLayout) mainPanel.getLayout()).show(mainPanel, card); }
}

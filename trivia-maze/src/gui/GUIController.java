package gui;

import javax.swing.*;
import java.awt.*;

public class GUIController {
    private final JPanel mainPanel;
    private final MenuBar menuBar;
    public GUIController() {
        JFrame frame = new JFrame("Trivia Maze");
        frame.setSize(1280, 720);
        mainPanel = new JPanel();
        CardLayout cards = new CardLayout();
        mainPanel.setLayout(cards);
        mainPanel.add(new MainMenuPanel(frame::dispose, () -> cards.show(mainPanel, "GAME")), "MAIN");
        mainPanel.add(new GamePanel(() -> cards.show(mainPanel, "MAIN")), "GAME");

        cards.show(mainPanel, "MAIN");
        menuBar = new MenuBar();


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.revalidate();
        frame.repaint();
    }
}

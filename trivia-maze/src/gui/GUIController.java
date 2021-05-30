package gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;

import javax.swing.*;
import java.awt.*;

public class GUIController {
    private final JPanel mainPanel;

    public GUIController() {
        try {
            // Themes from https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes
            FlatLaf.setup(new FlatNordIJTheme());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        JFrame frame = new JFrame("Trivia Maze");
        frame.setSize(1280, 720);
        mainPanel = new JPanel();
        CardLayout cards = new CardLayout();
        mainPanel.setLayout(cards);

        mainPanel.add(new MainMenuPanel(frame::dispose, () -> cards.show(mainPanel, "GAME")), "MAIN");
        mainPanel.add(new GamePanel(() -> cards.show(mainPanel, "MAIN")), "GAME");

        cards.show(mainPanel, "MAIN");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }
}

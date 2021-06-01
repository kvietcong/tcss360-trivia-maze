package gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
import java.awt.*;

public class GUIController {
    final public static GameState STATE = GameStateSimple.getInstance();
    private final ImageIcon imageIcon;
    public GUIController() {
        try {
            // Themes from https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes
            FlatLaf.setup(new FlatNordIJTheme());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        final JPanel framePanel;
        final CardLayout cards = new CardLayout();
        imageIcon= new ImageIcon("resources/mazeicon.PNG");
        JFrame frame = new JFrame("Trivia Maze");
        frame.setSize(1280, 720);
        framePanel = new JPanel();
        framePanel.setLayout(cards);

        framePanel.add(new GamePanel(() -> {
            cards.show(framePanel, "MAIN");
            STATE.initiateState();                  // Basically used to clear sounds
        }), "GAME");

        framePanel.add(new MainMenuPanel(frame::dispose,
                    () -> {
                        STATE.initiateState();
                        cards.show(framePanel, "GAME");
                    },
                    loadPath -> {
                        STATE.loadState(loadPath);
                        cards.show(framePanel, "GAME");
                    }
                    ), "MAIN");

        cards.show(framePanel, "MAIN");
        frame.setJMenuBar(new MenuBar());
        frame.setIconImage(imageIcon.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBar());
        frame.setVisible(true);
        frame.add(framePanel);
        frame.revalidate();
        frame.repaint();
    }
}

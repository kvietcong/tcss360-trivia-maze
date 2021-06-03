package gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import constants.C;
import state.GameState;
import state.GameStateSimple;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

public class GUIController {
    /** Current game state. */
    public static final GameState STATE = GameStateSimple.getInstance();

    /**
     * Creates an instance that handles much of the GUI interaction.
     */
    public GUIController() {
        try {
            FlatLaf.setup(new FlatNordIJTheme());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        JPanel framePanel;
        CardLayout cards = new CardLayout();
        ImageIcon imageIcon = new ImageIcon("resources/mazeicon.png");
        JFrame frame = new JFrame("Trivia Maze");
        frame.setSize(C.WIDTH, C.HEIGHT);
        framePanel = new JPanel();
        framePanel.setLayout(cards);

        framePanel.add(new GamePanel(() -> {
            cards.show(framePanel, "MAIN");
            STATE.initiateState(); // Basically used to clear sounds
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
        frame.setIconImage(imageIcon.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBar(
                loadPath -> {
                    STATE.loadState(loadPath);
                    cards.show(framePanel, "GAME");
                })
        );
        frame.setVisible(true);
        frame.add(framePanel);
        frame.revalidate();
        frame.repaint();
    }
}

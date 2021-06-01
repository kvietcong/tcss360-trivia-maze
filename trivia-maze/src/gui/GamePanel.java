package gui;

import state.GameState;
import state.GameState.RoomState;
import state.GameStateSimple;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static state.GameState.GameEvent;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private static final GameState STATE = GameStateSimple.getInstance();
    private final Runnable showMainMenu;

    /**
     * Initialize a new panel to handle game GUI
     * @param showMainMenu Go back to main menu
     */
    public GamePanel(Runnable showMainMenu) {
        this.showMainMenu = showMainMenu;
        STATE.addPropertyChangeListener(this);

        JPanel triviaPanel = new JPanel();
        setLayout(new CardLayout());
        add(new CurrentInfoPanel((room, question) -> {
            triviaPanel.removeAll();
            triviaPanel.add(new TriviaPanel(room, question, STATE, () -> show("CURRENT_INFO")));
            show("ANSWER_QUESTION");
            revalidate();
            repaint();
        }), "CURRENT_INFO");
        add(triviaPanel, "ANSWER_QUESTION");
    }

    /** Show specific card of the layout */
    private void show(String card) { ((CardLayout) getLayout()).show(this, card); }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param event A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        GameEvent gameEvent = GameEvent.valueOf(event.getPropertyName());
        switch (gameEvent) {
            case MOVE, LOAD, SAVE -> show("CURRENT_INFO");
            case ROOM_CHANGE -> {
                RoomState newState = (RoomState) event.getNewValue();
                if (newState == RoomState.UNLOCKED) {
                    JOptionPane.showMessageDialog(this, "You are Correct!",
                            "Correct", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "You are Incorrect :(",
                            "Incorrect", JOptionPane.ERROR_MESSAGE);
                }
                show("CURRENT_INFO");
            }
            case WIN, LOSE -> {
                add(new EndPanel(gameEvent, showMainMenu), "END");
                show("END");
            }
            default -> throw new IllegalStateException("Unexpected value: " + event.getPropertyName());
        }
        revalidate();
        repaint();
    }
}

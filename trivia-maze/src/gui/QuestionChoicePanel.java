package gui;

import maze.Room;
import question.Question;
import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import static state.GameState.GameEvent.*;

public class QuestionChoicePanel extends JPanel implements PropertyChangeListener {
    /** The size of the increase/decrease buttons. */
    private static final Dimension BUTTON_SIZE = new Dimension(50, 25);
    /** The amount of padding for the change panel. */
    private static final int HORIZONTAL_PADDING = 30;

    private final GameState gameState;

    // Constructor with no beginning choices
    public QuestionChoicePanel(GameState gameState) {
        super();
        this.gameState = gameState;
        gameState.addPropertyChangeListener(this);
    }

    // Constructor with beginning choices
    public QuestionChoicePanel(Question question, GameState gameState) {
        this(gameState);
        updateQuestion(question);
    }

    public void updateQuestion(Question newQuestion) {
        // Clear the Panel
        removeAll();
        // Build new buttons and add it to Panel
        constructChoiceButtons(newQuestion).forEach(this::add);
        // Make sure to update the UI
        revalidate();
        repaint();
    }

    private Set<JButton> constructChoiceButtons(Question question) {
        Set<JButton> newButtons = new HashSet<>();
        for (String choice : question.getChoices()) {
            JButton newButton = new JButton(choice);
            newButton.addActionListener(action -> {
                System.out.println("I am doing something");
                System.out.println("Or am I???");
            });
            newButton.setSize(BUTTON_SIZE);
            newButtons.add(newButton);
        }
        return newButtons;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param event A PropertyChangeEvent object describing the event source
     *              and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Do something on the ANSWER event
        if (ANSWER.toString().equals(event.getPropertyName())) {
            final Room newRoom = (Room) event.getNewValue();
            final Question newQuestion = gameState.getQuestion(newRoom);
            updateQuestion(newQuestion);
        }
    }
}

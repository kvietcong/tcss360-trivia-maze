package gui;

import question.Question;
import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import static state.GameState.GameEvent.*;

public class ChoicePanel extends JPanel implements PropertyChangeListener {
    private final GameState gameState;

    private Set<JButton> choiceButtons;
    private Question question;

    /** The size of the increase/decrease buttons. */
    private static final Dimension BUTTON_SIZE = new Dimension(50, 25);
    /** The amount of padding for the change panel. */
    private static final int HORIZONTAL_PADDING = 30;

    public ChoicePanel(Question question, GameState gameState) {
        super();
        this.gameState = gameState;
        gameState.addPropertyChangeListener(this);
        choiceButtons = new HashSet<>();
        updateQuestion(question);
    }

    public void updateQuestion(Question newQuestion) {
        question = newQuestion;
        removeAll();
        choiceButtons = constructChoiceButtons(newQuestion);
        choiceButtons.forEach(this::add);
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
        // Do something on the MOVE event
        if (MOVE.toString().equals(event.getPropertyName())) {
            final Question newQuestion = gameState.getCurrentQuestion();
            updateQuestion(newQuestion);
        }
    }
}

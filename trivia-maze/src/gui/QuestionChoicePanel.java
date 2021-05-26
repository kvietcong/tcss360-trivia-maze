package gui;

import maze.Room;
import question.Question;
import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class QuestionChoicePanel extends JPanel {
    // Constructor for a question panel
    public QuestionChoicePanel(Room room, Question question, GameState gameState) {
        setLayout(new GridLayout(0, 1));
        add(new JLabel("Question for " + room.toString()));
        add(new JLabel(question.getQuestion()));
        if (question.getType() != Question.QuestionType.SA) {
            Arrays.stream(question.getChoices()).forEach(choice -> {
                JButton newButton = new JButton(choice);
                newButton.addActionListener(action -> gameState.attemptQuestion(room, choice));
                add(newButton);
            });
        } else {
            JTextField textField = new JTextField();
            textField.addActionListener(action -> gameState.attemptQuestion(room, textField.getText()));
            add(textField);
        }

        revalidate();
        repaint();
    }
}

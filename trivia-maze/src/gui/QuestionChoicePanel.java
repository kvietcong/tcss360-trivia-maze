package gui;

import maze.Room;
import question.Question;
import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.function.Consumer;

public class QuestionChoicePanel extends JPanel {
    // Constructor for a question panel
    public QuestionChoicePanel(Room room, Question question, GameState gameState, Consumer<String> changeCard) {
        setLayout(new GridLayout(0, 1));

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        JLabel title = new JLabel("Question for " + room.toString());
        title.setFont(new Font("Serif", Font.BOLD, 48));
        container.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.ipady = 25;

        JLabel questionTitle = new JLabel(question.getQuestion());
        questionTitle.setFont(new Font("Serif", Font.BOLD, 36));
        center.add(questionTitle, gbc);

        JPanel answersContainer = new JPanel();
        answersContainer.setLayout(new FlowLayout());
        gbc.gridy++;
        center.add(answersContainer, gbc);

        if (question.getType() != Question.QuestionType.SA) {
            Arrays.stream(question.getChoices()).forEach(choice -> {
                JButton newButton = new JButton(choice);
                newButton.addActionListener(action -> gameState.attemptQuestion(room, choice));
                answersContainer.add(newButton);
            });
        } else {
            JTextField textField = new JTextField();
            textField.addActionListener(action -> gameState.attemptQuestion(room, textField.getText()));
            answersContainer.add(textField);
        }

        JButton leaveButton = new JButton("Back out");
        leaveButton.addActionListener(action -> changeCard.accept("CURRENT_INFO"));
        leaveButton.setBackground(Color.RED);
        gbc.gridy++;
        center.add(leaveButton, gbc);

        container.add(center, BorderLayout.CENTER);
        add(container);
        revalidate();
        repaint();
    }
}

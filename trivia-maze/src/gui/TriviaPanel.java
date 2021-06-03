package gui;

import constants.C;
import maze.Room;
import question.Question;
import state.GameState;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

public class TriviaPanel extends JPanel {
    /**
     * Creates a panel that represents a question to answer for a room.
     * @param room Room of the question
     * @param question Question to answer
     * @param gameState Current game state
     * @param showCurrentInfo Function to show current info panel
     */
    public TriviaPanel(Room room, Question question, GameState gameState, Runnable showCurrentInfo) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        JLabel title = new JLabel("Question for " + room.toString());
        title.setFont(new Font("Arial", Font.BOLD, C.H1));
        container.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.ipady = C.PADDING;

        JLabel questionTitle = new JLabel(question.getQuestion());
        questionTitle.setFont(new Font("Arial", Font.BOLD, C.H2));
        center.add(questionTitle, gbc);

        JPanel answersContainer = new JPanel();
        answersContainer.setLayout(new FlowLayout());
        gbc.gridy++;
        center.add(answersContainer, gbc);

        if (question.getType() != Question.QuestionType.SA) {
            Arrays.stream(question.getChoices()).forEach(choice -> {
                JButton newButton = new JButton(choice);
                newButton.addActionListener(action ->
                        gameState.attemptQuestion(room, choice));
                answersContainer.add(newButton);
            });
        } else {
            JTextField textField = new JTextField();
            textField.addActionListener(action ->
                    gameState.attemptQuestion(room, textField.getText()));
            answersContainer.add(textField);
        }

        JButton leaveButton = new JButton("Back out");
        leaveButton.addActionListener(action -> showCurrentInfo.run());
        leaveButton.setBackground(Color.RED);
        leaveButton.setFont(new Font("Arial", Font.BOLD, C.H3));
        leaveButton.setForeground(Color.WHITE);
        gbc.gridy++;
        center.add(leaveButton, gbc);

        setBorder(C.BORDER);
        container.add(center, BorderLayout.CENTER);
        add(container);
        revalidate();
        repaint();
    }
}

package gui;

import constants.C;
import maze.Room;
import question.Question;
import state.GameState;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JTextArea questionTitle = new JTextArea(question.getQuestion());
        questionTitle.setWrapStyleWord(true);
        questionTitle.setEditable(false);
        questionTitle.setLineWrap(true);
        questionTitle.setFont(new Font("Arial", Font.BOLD, C.H2));
        center.add(questionTitle);

        JPanel answersContainer = new JPanel();
        answersContainer.setLayout(new FlowLayout());
        center.add(answersContainer);

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
            textField.setColumns(C.PADDING);
            answersContainer.add(textField);
        }

        JButton leaveButton = new JButton("Back out");
        leaveButton.addActionListener(action -> showCurrentInfo.run());
        leaveButton.setBackground(Color.RED);
        leaveButton.setFont(new Font("Arial", Font.BOLD, C.H3));
        leaveButton.setForeground(Color.WHITE);
        JPanel centerButton = new JPanel();
        centerButton.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = C.INSET;
        gbc.ipady = C.PADDING;
        gbc.ipadx = C.PADDING;
        centerButton.add(leaveButton, gbc);
        center.add(centerButton);

        setBorder(C.BORDER);
        container.add(center, BorderLayout.CENTER);
        add(container);
        revalidate();
        repaint();
    }
}

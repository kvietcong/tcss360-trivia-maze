package main;
import question.Question;
import question.QuestionTF;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // All current code is for testing purposes
	    System.out.println("Hello World!");

        Question testQ = new QuestionTF("You are ok.", "false");
        JLabel question = new JLabel(testQ.getQuestion());
        JLabel prompt = new JLabel(testQ.getChoices()[0]);
        JButton trueButton = new JButton("True");
        JButton falseButton = new JButton("False");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(question);
        panel.add(prompt);
        panel.add(trueButton);
        panel.add(falseButton);

        JLabel correctLabel = new JLabel();
        panel.add(correctLabel);

        trueButton.addActionListener(event -> {
            boolean isSolved = testQ.isCorrectAnswer(trueButton.getText());
            correctLabel.setText(
                     isSolved ? "You are right" : "You are wrong");
            trueButton.setEnabled(!isSolved);
            falseButton.setEnabled(!isSolved);
        });
        falseButton.addActionListener(event -> {
            boolean isSolved = testQ.isCorrectAnswer(falseButton.getText());
            correctLabel.setText(
                    isSolved ? "You are right" : "You are wrong");
            trueButton.setEnabled(!isSolved);
            falseButton.setEnabled(!isSolved);
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(event -> {
            if (!trueButton.isEnabled()) {
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);
                correctLabel.setText("");
            }
        });
        panel.add(resetButton);

        JFrame frame = new JFrame("Basic UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,720);
        frame.add(panel);
        frame.setVisible(true);
    }
}

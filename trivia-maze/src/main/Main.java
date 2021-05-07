package main;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // All current code is for testing purposes
	    System.out.println("Hello World!");
        JFrame frame = new JFrame("Basic UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,720);

        Question testQ = new QuestionMC("You are ok", false, "false", "True/False");
        JLabel question = new JLabel(testQ.getQuestion());
        JLabel prompt = new JLabel(testQ.getPrompt());
        JButton trueButton = new JButton("True");
        JButton falseButton = new JButton("False");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(question);
        panel.add(prompt);
        panel.add(trueButton);
        panel.add(falseButton);

        JLabel correctLabel = new JLabel();
        panel.add(correctLabel);

        trueButton.addActionListener(event ->{
            correctLabel.setText(testQ.solve("True") ? "You are right" : "You are wrong");
        });
        falseButton.addActionListener(event ->{
            correctLabel.setText(testQ.solve("False") ? "You are right" : "You are wrong");
        });

        frame.add(panel);
        frame.setVisible(true);


    }
}

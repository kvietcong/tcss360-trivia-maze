package gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {
    private final Consumer<String> showCard;

    /**
     * Initialize the panel and frame.
     * */
    public MainMenuPanel(Runnable exit, Consumer<String> showCard) {
        setLayout(null);
        this.showCard = showCard;
        setBackground(Color.black);
        setVisible(true);
        //new game button
        JButton newGame = new JButton("New Game");
        newGame.setBounds(150,100,220,80);
        add(newGame);

        //options button
        JButton options = new JButton("Options");
        options.setBounds(150,250,220,80);
        add(options);

        //exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(150,400,220,80);
        add(exitButton);

        //action listener for the buttons
        newGame.addActionListener(action -> showCard.accept("GAME"));
        options.addActionListener(action -> {});
        exitButton.addActionListener(action -> exit.run());
    }
}

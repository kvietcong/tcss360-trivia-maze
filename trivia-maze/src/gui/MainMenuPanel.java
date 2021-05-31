package gui;

import javax.swing.*;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {
    /**
     * Initialize the panel and frame.
     * */
    public MainMenuPanel(Runnable exit, Runnable newGame, Consumer<String> loadGame) {
        setLayout(null);
        setVisible(true);
        //new game button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(150,100,220,80);
        add(newGameButton);

        //options button
        JButton options = new JButton("Options");
        options.setBounds(150,250,220,80);
        add(options);

        //exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(150,400,220,80);
        add(exitButton);

        //action listener for the buttons
        newGameButton.addActionListener(action -> newGame.run());
        options.addActionListener(action -> {});
        exitButton.addActionListener(action -> exit.run());
    }
}

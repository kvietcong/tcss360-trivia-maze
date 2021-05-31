package gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {

    private final GridBagConstraints gbc;
    /**
     * Initialize the main menu panel
     * */
    public MainMenuPanel(Runnable exit, Runnable newGame, Consumer<String> loadGame) {
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setConstraints();

        //new game button
        JButton newGameButton = new JButton("New Game");
        gbc.gridy++;
        add(newGameButton, gbc);

        //options button
        JButton options = new JButton("Options");
        gbc.gridy++;
        add(options,gbc);

        //exit button
        JButton exitButton = new JButton("Exit");
        gbc.gridy++;
        add(exitButton,gbc);

        //action listener for the buttons
        newGameButton.addActionListener(action -> newGame.run());
        options.addActionListener(action -> {});
        exitButton.addActionListener(action -> exit.run());
        options.addActionListener(action -> {});
        exitButton.addActionListener(action -> exit.run());
    }

    private void setConstraints(){
        gbc.insets = new Insets(20,20,20,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 20;
    }
}


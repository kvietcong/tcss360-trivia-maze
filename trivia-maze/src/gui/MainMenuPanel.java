package gui;

import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {

    private final GridBagConstraints gbc;
    private final GameState state =  GameStateSimple.getInstance();
    private final Consumer<String> loadPath;

    /**
     * Initialize the main menu panel
     * */
    public MainMenuPanel(Runnable exit, Runnable newGame, Consumer<String> loadPath) {
        this.loadPath = loadPath;

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setConstraints();

        //new game button
        JButton newGameButton = new JButton("New Game");
        gbc.gridy++;
        add(newGameButton, gbc);

        //options button
        JButton loadButton = new JButton("Load Game");
        gbc.gridy++;
        add(loadButton, gbc);

        //exit button
        JButton exitButton = new JButton("Exit");
        gbc.gridy++;
        add(exitButton,gbc);

        //action listener for the buttons
        newGameButton.addActionListener(action -> newGame.run());
        loadButton.addActionListener(action -> {});
        loadButton.addActionListener(action -> loadGame());
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

    private void loadGame(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionfilter = new FileNameExtensionFilter("Maze file(.maze)", "maze");
        int selectedOption;
        fileChooser.setDialogTitle("Open a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(extensionfilter);
        selectedOption = fileChooser.showOpenDialog(this);

        if (selectedOption == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            loadPath.accept(file.getPath());
        }
    }
}


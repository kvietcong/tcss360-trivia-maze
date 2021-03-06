package gui;

import constants.C;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {
    /** UI adding constraints. */
    private final GridBagConstraints gbc;
    /** Function to load a game at the given path. */
    private final Consumer<String> loadPath;

    /**
     * Initialize the main menu panel.
     */
    public MainMenuPanel(Runnable exit, Runnable newGame, Consumer<String> loadPath) {
        this.loadPath = loadPath;

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setConstraints();

        try {
            BufferedImage titleImage = ImageIO.read(new File("./resources/title.png"));
            JLabel imageLabel = new JLabel(new ImageIcon(titleImage));
            gbc.gridy++;
            add(imageLabel,  gbc);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        add(exitButton, gbc);

        //action listener for the buttons
        newGameButton.addActionListener(action -> newGame.run());
        loadButton.addActionListener(action -> loadGame());
        exitButton.addActionListener(action -> exit.run());
    }

    private void setConstraints() {
        gbc.insets = C.INSET;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = C.PADDING * 2;
        gbc.ipady = C.PADDING;
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser(C.PATH);
        FileNameExtensionFilter extensionFilter =
                new FileNameExtensionFilter("Maze file(.maze)", "maze");
        int selectedOption;
        fileChooser.setDialogTitle("Open a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(extensionFilter);
        selectedOption = fileChooser.showOpenDialog(this);

        if (selectedOption == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            loadPath.accept(file.getPath());
        }
    }
}


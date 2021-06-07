package gui;

import constants.C;
import state.GameState;
import state.GameStateSimple;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MenuBar extends JMenuBar {
    /** Current Game State. */
    private final GameState state = GameStateSimple.getInstance();
    /** File Menu. */
    private final JMenu fileMenu;
    /** Help menu. */
    private final JMenu helpMenu;
    /** Save Menu. */
    private final JMenuItem saveItem;
    /** Load Menu. */
    private final JMenuItem loadItem;
    /** Entry to show main menu. */
    private final JMenuItem mainMenuItem;
    /** About Menu. */
    private final JMenuItem aboutItem;
    /** Instruction Menu. */
    private final JMenuItem instructionItem;
    /** Cheat Menu. */
    private final  JMenuItem cheatItem;
    /** File chooser. */
    private final JFileChooser fileChooser;
    /** Text Label for the game instruction. */
    private final JTextArea textField;
    /** Image for the about menu. */
    private final ImageIcon image;
    /** Extension filter for saving and loading. */
    private final FileNameExtensionFilter extensionFilter;
    /** Load a path. */
    private final Consumer<String> loadPath;
    /** Representation of the selected Option. */
    private int selectedOption;

    /**
     * Initializes the menu bar.
     * @param loadPath Function to load the game at the given path.
     * @param showMainMenu Function to go back to the main menu.
     */
    public MenuBar(Consumer<String> loadPath, Runnable showMainMenu) {
        this.loadPath = loadPath;
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        mainMenuItem = new JMenuItem("Go to Main Menu");
        saveItem = new JMenuItem("Save...");
        loadItem = new JMenuItem("Load...");
        aboutItem = new JMenuItem("About...");
        instructionItem = new JMenuItem("Game Instruction");
        cheatItem = new JMenuItem("Cheat");
        fileChooser  = new JFileChooser(C.PATH);
        textField = new JTextArea();
        image = new ImageIcon("resources/mazeicon.png");
        extensionFilter = new FileNameExtensionFilter(
                "Maze file(.maze)", "maze");
        //action listener for menu items
        saveItem.addActionListener(action -> saveFile());
        loadItem.addActionListener(action -> loadFile());
        aboutItem.addActionListener(acton -> aboutMenu());
        mainMenuItem.addActionListener(action -> showMainMenu.run());
        instructionItem.addActionListener(action -> info());
        cheatItem.addActionListener(action -> cheatMenu());
        createMenuBar();
        initializeMnemonicAndAccelerator();
    }

    /** Creates a menu bar. */
    private void createMenuBar() {
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(mainMenuItem);

        helpMenu.add(instructionItem);
        helpMenu.add(cheatItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        add(fileMenu);
        add(helpMenu);
    }

    /** Initializes mnemonic and accelerator keys. */
    private void initializeMnemonicAndAccelerator() {
        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        loadItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        instructionItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
    }
    /** This method shows the message dialog for about menu.*/
    private void aboutMenu() {
        image.setImage(image.getImage().
                getScaledInstance(C.H1, -1, Image.SCALE_SMOOTH));
        JOptionPane.showMessageDialog(this,
                "Created by: KV, Brandon, and Merit" + "\n"
                + "TCSS 360 Trivia Maze", "About",
                JOptionPane.INFORMATION_MESSAGE, image);
    }
    /** Displays game instruction. */
    private void info() {
        StringBuilder instructions = new StringBuilder();
        instructions
                .append("The title on the top shows the room id you're currently in.")
                .append("\n\n")
                .append("Neighboring rooms are represented in buttons and aligned next to each other.")
                .append("\n\n")
                .append("Green represents an unlocked room, ")
                .append("yellow represents unknown rooms and red represents locked rooms")
                .append("\n\n")
                .append("Each room has room id, topics, and the distance from the end. ")
                .append("Each time you pass through unknown rooms you have to answer a question")
                .append("\n\n")
                .append("If you answer a question correctly, you unlock the room, allowing you to go into it. ")
                .append("If you get it wrong the room will be locked forever")
                .append("\n\n")
                .append("When passing through green rooms the progress bar increases based on the shorter distance. ")
                .append("The progress bar also decreases if you have a long distance")
                .append("\n\n")
                .append("To win this game try to pass through yellow rooms and answer questions correctly, ")
                .append("which will take you to the next rooms. ")
                .append("From there try passing through green rooms with a shorter distance to reach the end.")
                .append("\n\n")
                .append("Enjoy the Game :)");

        textField.setText(instructions.toString());
        textField.setEditable(false);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);

        JScrollPane scroller = new JScrollPane(textField);
        scroller.setPreferredSize(C.BIG_DIALOG);
        scroller.getVerticalScrollBar().setValue(0);
        JOptionPane.showMessageDialog(this, scroller,
                "How to play", JOptionPane.INFORMATION_MESSAGE);

    }
    /** Display cheat menu. */
    private void cheatMenu() {
        try {
            state.moveToRoom(state.getEndRoom());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this,
                    "Please start a game", "Cheat",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * This method saves the game state info.
     */
    private void saveFile() {
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(extensionFilter);
        selectedOption = fileChooser.showSaveDialog(this);

        if (selectedOption == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            state.saveState(file.getPath() + ".maze");
        }
    }

    /**
     * This method loads the game state given the path.
     */
    private void loadFile() {
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

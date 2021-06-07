package gui;

import constants.C;
import maze.Room;
import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
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
    /**Instruction Menu*/
    private final JMenuItem instructionItem;
    /**Cheat Menu*/
    private final  JMenuItem cheatItem;
    /** File chooser. */
    private final JFileChooser fileChooser;
    /**Text Label for the game instruction*/
   private final JTextArea textfield;
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
        textfield = new JTextArea();
        image = new ImageIcon("resources/mazeicon.png");
        extensionFilter = new FileNameExtensionFilter(
                "Maze file(.maze)", "maze");
        //action listener for menu items
        saveItem.addActionListener(action -> saveFile());
        loadItem.addActionListener(action -> loadFile());
        aboutItem.addActionListener(acton -> aboutMenu());
        mainMenuItem.addActionListener(action -> showMainMenu.run());
        instructionItem.addActionListener(action -> info());
        cheatItem.addActionListener(action -> cheatmenu());
        createMenuBar();
        initializeMnemonicAndAccelerator();
    }

    /**Creates a menu bar.*/
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

    /**Initializes mnemonic and accelerator keys.*/
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
    /**Displays game instruction*/
    private void info(){
     textfield.setText("""
             The title on the top shows the room id you're currently in
             Neighboring rooms are represented in buttons and aligned next to each other
             Green represents an unlocked room, yellow represents unknown rooms and red represents locked rooms
             Each room has room id, topics, and the distance from the end
             Each time you pass through unknown rooms you have to answer a question
              If you answer a question correctly, it passes to the next rooms. If you get it wrong the room will be locked forever
             When passing through green rooms the progress bar increases based on the shorter distance.
             The progress bar also decreases if you have a long distance
             To win this game try to pass through yellow rooms and answer questions correctly. Which will take you to the next rooms. From there try passing through green rooms with a shorter distance to reach the end.
             Enjoy the game:)"""

     );
     textfield.setEditable(false);
     textfield.setLineWrap(true);
     textfield.setWrapStyleWord(true);
    JOptionPane.showMessageDialog(this, new JScrollPane(textfield),"How to play",JOptionPane.OK_OPTION);

    }
    /**Display cheat menu*/
    private void cheatmenu(){
    try {
        Room room = state.getCurrentRoom();
        state.moveToRoom(room);
        JLabel label = new JLabel();
        label.setText(room.toString());
        JOptionPane.showMessageDialog(this, label, "Cheat", JOptionPane.OK_OPTION);
    }
    catch (NullPointerException e){

        JLabel label1 = new JLabel("Please start the game");
        JOptionPane.showMessageDialog(this,label1,"",JOptionPane.WARNING_MESSAGE);
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

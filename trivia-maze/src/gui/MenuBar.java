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
    /** File chooser. */
    private final JFileChooser fileChooser;
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
        fileChooser  = new JFileChooser(C.PATH);
        image = new ImageIcon("resources/mazeicon.png");
        extensionFilter = new FileNameExtensionFilter(
                "Maze file(.maze)", "maze");
        //action listener for menu items
        saveItem.addActionListener(action -> saveFile());
        loadItem.addActionListener(action -> loadFile());
        aboutItem.addActionListener(acton -> aboutMenu());
        mainMenuItem.addActionListener(action -> showMainMenu.run());

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

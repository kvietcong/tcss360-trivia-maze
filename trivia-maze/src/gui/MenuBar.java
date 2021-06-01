package gui;

import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {

    private final GameState state = GameStateSimple.getInstance();
    /**File Menu.*/
    private final JMenu File;
    /**Help menu*/
    private final JMenu Help;
    /**Save Menu*/
    private final JMenuItem Save;
    /**Load Menu*/
    private final JMenuItem Load;
    /**About Menu*/
    private final JMenuItem About;
    /**File chooser*/
    private final JFileChooser fileChooser;

    private final ImageIcon image;
    int result;
    /**Initializes the menu bar*/
    public MenuBar() {
        File = new JMenu("File");
        Help = new JMenu("Help");
        Save = new JMenuItem("Save...");
        Load = new JMenuItem("Load...");
        About = new JMenuItem("About...");
        fileChooser  = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        image = new ImageIcon("resources/mazeicon.PNG");
        //action listener for menu items
        Save.addActionListener(action -> saveFile());
        Load.addActionListener(action -> loadFile());
        About.addActionListener(acton -> aboutMenu());

        createMenubar();
        createMnemonic();
    }
    /**Creates a menu bar.*/
    private void createMenubar() {
        File.add(Save);
        File.addSeparator();
        File.add(Load);
        Help.add(About);
        add(File);
        add(Help);
    }
    /**Creates mnemonic.*/
    private void createMnemonic() {
        File.setMnemonic(KeyEvent.VK_F);
        Help.setMnemonic(KeyEvent.VK_H);
        Save.setMnemonic(KeyEvent.VK_S);
        Load.setMnemonic(KeyEvent.VK_L);
        About.setMnemonic(KeyEvent.VK_A);

    }
    /**This method shows the message dialog for about menu.*/
    private void aboutMenu(){
       image.setImage(image.getImage().getScaledInstance(50,-1, Image.SCALE_SMOOTH));
        JOptionPane.showMessageDialog(null,
                "KV,Brandon,Merit" + "\n"
                 + "TCSS 360 Trivia Maze","About",JOptionPane.INFORMATION_MESSAGE,image);
    }
    /**
     * This method saves the game state info.
     * */
    private void saveFile() {
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            state.saveState(file.getPath());
        }
    }
    /**
     * This method loads the game state given the path.
     * */
    private void loadFile() {
        fileChooser.setDialogTitle("Open a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            state.loadState(file.getPath());
        }
    }
}

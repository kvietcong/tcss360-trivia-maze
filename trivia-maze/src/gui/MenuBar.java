package gui;

import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuBar extends JMenuBar {
    private final GameState state = GameStateSimple.getInstance();
    private final JMenu File;
    private  final JMenu About;
    private final JMenuItem Save;
    private final JMenuItem Load;
    java.io.File file;
    Scanner fileIn;
    int response;
    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    //private final GameState state = GameStateSimple.getInstance();


    public MenuBar() {
        File = new JMenu("File");
        About = new JMenu("About");
        Save = new JMenuItem("Save...");
        Load = new JMenuItem("Load...");

        createMenubar();
        createMnemonic();
        Save.addActionListener(action -> saveFile());
        Load.addActionListener(action-> loadFile());
    }
    private void createMenubar(){
        File.add(Save);
        File.addSeparator();
        File.add(Load);
        add(File);
        add(About);
    }
    private void createMnemonic(){
        File.setMnemonic(KeyEvent.VK_F);
        About.setMnemonic(KeyEvent.VK_A);
        Save.setMnemonic(KeyEvent.VK_S);
        Load.setMnemonic(KeyEvent.VK_L);
    }
    private void saveFile() {
        fileChooser.setDialogTitle("Save to file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
           // String fileName = fileToSave;

            //System.out.println(fileName);
        }
    }

    private void loadFile(){
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            try {
                fileIn = new Scanner(file);
                if(file.isFile()){

                    while(fileIn.hasNextLine()){
                        String line = fileIn.nextLine();
                        state.loadState(line);
                    }
                }
                else{
                    System.out.println("That was not a file!");
                }
                fileIn.close();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}

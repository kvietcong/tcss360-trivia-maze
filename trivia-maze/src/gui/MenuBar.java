package gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private final JMenu File;
    private  final JMenu About;
    private final JMenuItem Save;
    private final JMenuItem Load;

    public MenuBar(){
        File = new JMenu("File");
        About = new JMenu("About");
        Save = new JMenuItem("Save...");
        Load = new JMenuItem("Load...");
        createMenubar();

    }
    private void createMenubar(){
        File.add(Save);
        File.addSeparator();
        File.add(Load);
        add(File);
        add(About);
    }

}

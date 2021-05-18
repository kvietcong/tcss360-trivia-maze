package gui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    MazePanel mazePanel;
    Maze maze;
    JButton up;
    JButton down;
    JButton right;
    JButton left;
    JMenuBar menubar;
    JMenu file;
    JMenu about;
    public GamePanel(){
        setLayout(null);
        mazePanel = new MazePanel();
        menubar = new JMenuBar();
        file = new JMenu("File");
        about = new JMenu("About");
        up = new JButton("Up");
        down = new JButton("Down");
        right = new JButton("Right");
        left = new JButton("Left");
        mazePanel.setBounds(50,50,300,300);
        menubar.add(file);
        menubar.add(about);
        createButtons();
        add(mazePanel);
        add(menubar);


        up.setVisible(true);
        add(menubar);
        add(up);
        add(down);
        add(right);
        add(left);
        setBounds(0,0,1000,600);
        setBackground(Color.black);
    }
    private void createButtons(){
        up.setBounds(100,510,100,20);
        down.setBounds(100,550,100,20);
        left.setBounds(10,530,100,20);
        right.setBounds(200,530,100,20);
    }

}

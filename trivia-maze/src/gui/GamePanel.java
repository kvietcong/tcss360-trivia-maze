package gui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    /**The maze panel.*/
    MazePanel mazePanel;
    Maze maze;
    JButton up;
    JButton down;
    JButton right;
    JButton left;
    /**The menu bar.*/
    JMenuBar menubar;
    /**The file menu.*/
    JMenu file;
    /**The about menu.*/
    JMenu about;

    /**
     * Initialize the maze panel and menu bar.
     * */
    public GamePanel(){
        setLayout(null);
        mazePanel = new MazePanel();
        menubar = new JMenuBar();

        /*up = new JButton("Up");
        down = new JButton("Down");
        right = new JButton("Right");
        left = new JButton("Left");*/
        mazePanel.setBounds(50,50,300,300);
        createMenu();
       // createButtons();
        add(mazePanel);
        add(menubar);
        //add(up);
       // add(down);
        //add(right);
       // add(left);

        setBounds(0,0,1000,600);

    }
 /*   private void createButtons(){
        up.setBounds(100,510,100,20);
        down.setBounds(100,550,100,20);
        left.setBounds(10,530,100,20);
        right.setBounds(200,530,100,20);
    }*/
    /**Creates the menu*/
    private void createMenu(){
        file = new JMenu("File");
        about = new JMenu("About");
        menubar.add(file);
        menubar.add(about);
    }
}

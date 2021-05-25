package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class MainMenu extends JFrame {
    /**The main panel for the main menu.*/
    private final JPanel mainpanel;

    /**
     * Initialize the panel and frame.
     * */
    public MainMenu(){
        super("Trivia Maze");
        mainpanel = new JPanel();
        setContentPane(mainpanel);
        mainpanel.setLayout(null);
        mainpanel.setBounds(300,50,700,600);
        mainpanel.setBackground(Color.black);
        setVisible(true);
        initializeMenu();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    /**
     * Initializes the menu buttons.
     * */
    private void initializeMenu(){
        //new game button
        JButton newGame = new JButton("New Game");
        newGame.setBounds(150,100,220,80);
        mainpanel.add(newGame);
        //options button
        JButton options = new JButton("Options");
        options.setBounds(150,250,220,80);
        mainpanel.add(options);
        //exit button
        JButton exit = new JButton("Exit");
        exit.setBounds(150,400,220,80);
        mainpanel.add(exit);
        //action listener for the buttons
        newGame.addActionListener(newGamebtn);
        options.addActionListener(optionsbtn);
        exit.addActionListener(exitbtn);
    }
    /**Action Listener for new game button.*/
    ActionListener newGamebtn = event -> {
        setContentPane(new GamePanel());
        revalidate();
        repaint();
    };
    /**Action Listener for options button.*/
    ActionListener optionsbtn = event -> {
    };
    /**Action listener for exit button.*/
    ActionListener exitbtn = event -> {
        System.exit(0);
    };
}

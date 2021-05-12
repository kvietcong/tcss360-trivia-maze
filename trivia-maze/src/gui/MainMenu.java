package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel panel;
    public static void main(String[] args) {
            new MainMenu();

    }

    public MainMenu(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Trivia Maze");
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(100,100,650,300);
        setContentPane(panel);
        setSize(720,650);
        setVisible(true);

        JButton newGame = new JButton("New Game");
        newGame.setBounds(150,100,220,80);
        panel.add(newGame);

        JButton options = new JButton("Options");
        options.setBounds(150,250,220,80);
        panel.add(options);

        JButton exit = new JButton("Exit");
        exit.setBounds(150,400,220,80);
        panel.add(exit);

        newGame.addActionListener(newGamebtn);
        options.addActionListener(optionsbtn);
        exit.addActionListener(exitbtn);
    }
    ActionListener newGamebtn = e -> {

    };
    ActionListener optionsbtn = e -> {

    };
    ActionListener exitbtn = e -> {

    };

}

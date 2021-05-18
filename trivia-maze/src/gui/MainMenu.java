package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class MainMenu extends JFrame {
    private JPanel panel;
    private MazePanel mazePanel;

    public MainMenu(){
        super("Trivia Maze");
        //setBounds(300,50,700,500);
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setBounds(300,50,700,600);
        panel.setBackground(Color.black);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeMenu();

    }

    private void initializeMenu(){
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
    ActionListener newGamebtn = event -> {
        setContentPane(new GamePanel());
    };
    ActionListener optionsbtn = event -> {

    };
    ActionListener exitbtn = event -> {
        System.exit(0);
    };
}

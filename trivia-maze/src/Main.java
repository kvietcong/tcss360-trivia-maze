import gui.MainMenu;

import javax.swing.*;

public class Main {
    private JFrame frame;
    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        frame = new MainMenu();
        frame.setBounds(200,10,1000,650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

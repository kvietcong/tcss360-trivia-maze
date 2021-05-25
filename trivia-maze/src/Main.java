import gui.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {
    /**The main frame GUI */
    private final JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }

    public Main(){
        frame = new MainMenu();
        frame.setBounds(200,10,1000,650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

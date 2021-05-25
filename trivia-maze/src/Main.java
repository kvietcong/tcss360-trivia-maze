import gui.GUIController;
import gui.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
        EventQueue.invokeLater(GUIController::new);
    }

    public Main() {
        /* The main frame GUI */
        JFrame frame = new MainMenu();
        frame.setBounds(200,10,1000,650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

package gui;

import state.GameState;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    public EndPanel(GameState.GameEvent status, Runnable showMainMenu) {
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new GridBagLayout());

        JLabel title = new JLabel(status == GameState.GameEvent.WIN ? "You Won!!!" : "You Lost :(");
        title.setFont(new Font("Arial", Font.BOLD, 72));
        center.add(title, new GridBagConstraints());

        JButton mainMenu = new JButton("Return to Main Menu");
        mainMenu.addActionListener(action -> showMainMenu.run());
        center.add(mainMenu);

        revalidate();
        repaint();
    }
}

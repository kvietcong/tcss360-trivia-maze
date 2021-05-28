package gui;

import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class EndPanel extends JPanel {
    public EndPanel(GameState.GameEvent status, Consumer<String> showCard) {
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new GridBagLayout());

        JLabel title = new JLabel(status == GameState.GameEvent.WIN ? "You Won!!!" : "You Lost :(");
        title.setFont(new Font("Serif", Font.BOLD, 72));
        center.add(title, new GridBagConstraints());

        JButton mainMenu = new JButton("Return to Main Menu");
        mainMenu.addActionListener(action -> showCard.accept("MAIN"));
        center.add(mainMenu);

        revalidate();
        repaint();
    }
}

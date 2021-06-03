package gui;

import state.GameState;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class EndPanel extends JPanel {
    /**
     * Create a panel to show the ending.
     * @param status A win or lose status.
     * @param showMainMenu Function to show main menu
     */
    public EndPanel(GameState.GameEvent status, Runnable showMainMenu) {
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        JLabel title = new JLabel(UI.wrapHTML(
                status == GameState.GameEvent.WIN
                        ? "You Won!!!"
                        : "You Lost :("));
        title.setFont(new Font("Arial", Font.BOLD, UI.H1 * 2));
        center.add(title, gbc);

        gbc.gridy++;
        JButton mainMenu = new JButton("Return to Main Menu");
        mainMenu.addActionListener(action -> showMainMenu.run());
        center.add(mainMenu, gbc);

        revalidate();
        repaint();
    }
}

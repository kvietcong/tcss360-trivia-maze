package gui;

import javax.swing.*;

public class TriviaUI extends JFrame {

    public TriviaUI() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                runGame();
            }
        });
    }

    private void runGame() {
        // Create all necessary components and add them to the display
        JFrame mainWindow = new JFrame("Calculator");
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel mazeUI = makeMazeUI();
        JPanel triviaUI = makeTriviaUI();

        main.add(mazeUI);
        main.add(triviaUI);

        mainWindow.add(main);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    private JPanel makeMazeUI() {
        return new JPanel();
    }

    private JPanel makeTriviaUI() {
        return new JPanel();
    }
}

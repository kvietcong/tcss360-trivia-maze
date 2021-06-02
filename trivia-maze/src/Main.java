import database.TriviaDatabaseConnection;
import gui.GUIController;

import java.awt.EventQueue;

public final class Main {
    private Main() { }

    /**
     * Entry point into the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(GUIController::new);
        // Makes sure the database connection is not left open
        Runtime.getRuntime().addShutdownHook(new Thread(() -> TriviaDatabaseConnection.getConnection().close()));
    }
}

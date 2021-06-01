import database.TriviaDatabaseConnection;
import gui.GUIController;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(GUIController::new);
        // Makes sure the database connection is not left open
        Runtime.getRuntime().addShutdownHook(new Thread(() -> TriviaDatabaseConnection.getConnection().close()));
    }
}

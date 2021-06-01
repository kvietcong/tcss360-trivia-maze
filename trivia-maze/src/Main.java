import database.TriviaDatabaseConnection;
import gui.GUIController;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(GUIController::new);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("closing database");
            TriviaDatabaseConnection.getConnection().close();
        }));
    }
}

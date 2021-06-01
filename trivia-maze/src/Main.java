import database.TriviaDatabaseConnection;
import gui.GUIController;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(GUIController::new);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("closing database");
                TriviaDatabaseConnection.getConnection().close();
            }
        });
    }
}

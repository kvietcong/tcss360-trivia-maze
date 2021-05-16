package database;

import java.util.List;
import java.util.Map;

/**
 * Wrapper class
 * Wraps a DatabaseConnection for usage with the trivia.db database.
 */
public class TriviaDatabaseConnection implements Database {

    private final DatabaseConnection dbc;
    private static final TriviaDatabaseConnection TDBC = new TriviaDatabaseConnection();

    private TriviaDatabaseConnection() {
        this.dbc = new DatabaseConnection("trivia.db");
    }

    public static TriviaDatabaseConnection getInstance() {
        return TDBC;
    }

    @Override
    public List<Map<String, String>> getData() {
        return this.dbc.getData("questions");
    }

    @Override
    public void close() {
        this.dbc.close();
    }

    public static void main(String[] args)
    {
        // Test getting a connection to the trivia database and print the content
        TriviaDatabaseConnection tdbc = TriviaDatabaseConnection.getInstance();
        List<Map<String, String>> questions = tdbc.getData();
        for (Map<String, String> q : questions) {
            System.out.println(q);
        }
    }
}

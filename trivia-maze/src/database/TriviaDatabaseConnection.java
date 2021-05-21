package database;

import java.util.List;
import java.util.Map;

/**
 * Wraps a DatabaseConnection for usage with the trivia.db database.
 */
public class TriviaDatabaseConnection implements Database {

    private final DatabaseConnection dbc;
    private static final TriviaDatabaseConnection TDBC = new TriviaDatabaseConnection();

    /**
     * Creates a TriviaDatabaseConnection.
     */
    private TriviaDatabaseConnection() {
        this.dbc = new DatabaseConnection("trivia.db");
    }

    /**
     * Gets a connection to the trivia  database.
     * @return Connection to the trivia database.
     */
    public static TriviaDatabaseConnection getConnection() {
        return TDBC;
    }

    /**
     * Gets all data from all tables from the trivia database.
     * Each key is a table name, and its value is the list of data from that table.
     * @return Map of tables and their data from the database.
     */
    @Override
    public Map<String, List<Map<String, String>>> getData() {
        return this.dbc.getData();
    }

    /**
     * Closes the connection to the trivia database.
     */
    @Override
    public void close() {
        this.dbc.close();
    }

    public static void main(String[] args)
    {
        // test.Test getting a connection to the trivia database and print the content
        TriviaDatabaseConnection tdbc = TriviaDatabaseConnection.getConnection();
        List<Map<String, String>> questions = tdbc.getData().get("questions");
        for (Map<String, String> q : questions) {
            System.out.println(q);
        }
        tdbc.close();
    }
}

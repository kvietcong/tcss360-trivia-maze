package database;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Wraps a DatabaseConnection for usage with the trivia.db database.
 */
public class TriviaDatabaseConnection implements Database {

    private final DatabaseConnection dbc;
    private static final TriviaDatabaseConnection TDBC = new TriviaDatabaseConnection(
            "./resources/trivia-questions-1.txt",
            "./resources/trivia.db"
    );

    /**
     * Creates a TriviaDatabaseConnection.
     */
    private TriviaDatabaseConnection(String inputPath, String outputPath) {
        if (!new File(outputPath).isFile()) {
            // Questions from https://www.quizbreaker.com/trivia-questions
            TriviaDatabaseUploader.upload(inputPath, outputPath);
        }
        this.dbc = new DatabaseConnection(outputPath);
    }

    /**
     * Gets a connection to the trivia  database.
     * @return Connection to the trivia database.
     */
    public static TriviaDatabaseConnection getConnection() {
        return TDBC;
    }

    /**
     * Gets a connection to a new trivia database with the given file input path and database output path.
     * NOT RECOMMENDED FOR NORMAL USE. USE WITH CAUTION.
     * @param inputPath Path of the input file to upload to the database if it doesn't exist.
     * @param outputPath Path of the database file to use/create.
     * @return Connection to database resulting from the given paths.
     */
    public static TriviaDatabaseConnection getConnectionFromPath(String inputPath, String outputPath) {
        return new TriviaDatabaseConnection(inputPath, outputPath);
    }

    /**
     * Gets all data from all tables from the trivia database.
     * Each key is a table name, and its value is the list of data from that table.
     * @return Map of tables and their data from the database.
     */
    @Override
    public Map<String, Table> getData() {
        return this.dbc.getData();
    }

    /**
     * Closes the connection to the trivia database.
     */
    @Override
    public void close() {
        this.dbc.close();
    }
}

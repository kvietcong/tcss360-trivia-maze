package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TriviaDatabaseUploader {
    /**
     * Reads the formatted file at the given input path and uploads it to a SQLite database at the given output path.
     * If the database of the given path and name does not exist yet, it will be created.
     * If the database already contains a table of questions, it will be overwritten.
     * @param inputPath Path of the input file to read question data from (.txt).
     * @param outputPath Path of the output file to create the database in (.db).
     */
    public static void upload(String inputPath, String outputPath) {
        try {
            Scanner input = new Scanner(new File(inputPath));

            Connection conn = null;

            conn = DriverManager.getConnection("jdbc:sqlite:" + outputPath);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists questions");
            statement.executeUpdate("create table questions (id integer, question string, choices string, answer string, type string, topics string)");

            int count = 1;
            while (input.hasNextLine()) {
                String query ="INSERT INTO questions values(?,?,?,?,?,?)";
                String question = input.nextLine();
                String choices = input.nextLine();
                String answer = input.nextLine();
                String type = input.nextLine();
                String topics = input.nextLine();
                PreparedStatement prepStatement = conn.prepareStatement(query);
                prepStatement.setString(1, "" + count);
                prepStatement.setString(2, question);
                prepStatement.setString(3, choices);
                prepStatement.setString(4, answer);
                prepStatement.setString(5, type);
                prepStatement.setString(6, topics);
                prepStatement.executeUpdate();
                count++;
            }
            System.out.println("Success");
            conn.close();
        } catch (SQLException|FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}

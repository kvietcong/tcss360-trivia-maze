package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseUploader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/database/trivia_questions.txt"));

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:trivia.db");
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists questions");
            statement.executeUpdate("create table questions (id integer, question string, choices string, answer string, type string, topics string)");

            int count = 1;
            while (input.hasNextLine()) {
                String question = input.nextLine();
                String choices = input.nextLine();
                String answer = input.nextLine();
                String type = input.nextLine();
                String topics = input.nextLine();
                statement.executeUpdate(
                        String.format(
                                "insert into questions values('%s', '%s', '%s', '%s', '%s', '%s')",
                                count, question, choices, answer, type, topics
                        )
                );
                count++;
            }
            System.out.println("Success");
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

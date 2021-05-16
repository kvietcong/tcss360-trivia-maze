package database;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseUploader {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Scanner input = new Scanner(new File("src/database/trivia_questions.txt"));

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:trivia.db");
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists questions");
            statement.executeUpdate("create table questions (id integer, question string, choices string, answer string)");

            int count = 1;
            while (input.hasNextLine()) {
                String question = input.nextLine();
                String choices = input.nextLine();
                String answer = input.nextLine();
                statement.executeUpdate(
                        String.format(
                                "insert into questions values('%s', '%s', '%s', '%s')",
                                count, question, choices, answer
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

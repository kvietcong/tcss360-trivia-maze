package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TriviaDatabaseUploader {
    public static void upload(String path) {
        try {
            // ./trivia-questions-1.txt
            Scanner input = new Scanner(new File(path));

            Connection conn = null;

            conn = DriverManager.getConnection("jdbc:sqlite:./resources/trivia.db");
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

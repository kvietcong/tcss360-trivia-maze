package database;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection(String name) {
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + name);

            // Create a small table of questions for testing
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists questions");
            statement.executeUpdate("create table questions (id integer, question string, answers string)");
            statement.executeUpdate("insert into questions values(1, 'Are you good?', 'True|False')");
            statement.executeUpdate("insert into questions values(2, 'Are you sure?', 'Yes|No|Maybe|I don''t know')");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Map<String, String>> getData(String table) {
        try {
            List<Map<String, String>> data = new ArrayList<>();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("select * from " + table);

            // Get the set of column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            Set<String> colNames = new HashSet<>();
            for (int i = 1; i <= numCols; i++) {
                colNames.add(rsmd.getColumnName(i));
            }

            // Make an entry in the map for each column and its value
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (String n : colNames) {
                    row.put(n, rs.getString(n));
                }
                data.add(row);
            }

            return data;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

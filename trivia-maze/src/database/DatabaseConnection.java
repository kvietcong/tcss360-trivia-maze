package database;

import java.sql.*;
import java.util.*;

public class DatabaseConnection implements Database{

    private Connection connection;

    /**
     * Creates a connection to a SQLite database with the given name.
     * @param name Name of the database
     */
    public DatabaseConnection(String name) {
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + name);

            // Create a small table of questions for testing
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

//            statement.executeUpdate("drop table if exists questions");
//            statement.executeUpdate("create table questions (id integer, question string, choices string, answer string)");
//            statement.executeUpdate("insert into questions values(1, 'Are you good?', 'True|False', 'True')");
//            statement.executeUpdate("insert into questions values(2, 'Are you sure?', 'Yes|No|Maybe|I don''t know', 'Yes')");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gets all data from all tables from the database.
     * Each key is a table name, and its value is the list of data from that table.
     * @return Map of tables and their data from the database.
     */
    @Override
    public Map<String, List<Map<String, String>>> getData() {
        try {
            // Create a map of table names to table data
            // Each table's data is a list of maps, where each map represents a row by
            // mapping the column name to its entry.
            Map<String, List<Map<String, String>>> tables = new HashMap<>();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the data for each table and add it to the map
            ResultSet tablesInfo = connection.getMetaData().getTables(null, null, null,  null);
            while (tablesInfo.next()) {
                String tableName = tablesInfo.getString("TABLE_NAME");
                tables.put(tableName, this.getTableData(tableName, statement));
            }

            return tables;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets all the data from the given table using the given connection statement.
     * Each entry in the list is a row that maps column names to their value.
     * @param name Name of the table to retrieve
     * @param statement Connection statement to use to retrieve data
     * @return List of rows from the table
     */
    private List<Map<String, String>> getTableData(String name, Statement statement) {
        try {
            List<Map<String, String>> tableData = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select * from " + name);

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
                tableData.add(row);
            }

            return tableData;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Closes the connection to the database.
     */
    @Override
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

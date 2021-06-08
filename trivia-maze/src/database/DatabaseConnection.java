package database;

import java.sql.*;
import java.util.*;

public class DatabaseConnection implements Database {

    private Connection connection;

    /**
     * Creates a connection to a SQLite database with the given name.
     * @param path Path of the database
     */
    public DatabaseConnection(String path) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
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
    public Map<String, Table> getData() {
        try {
            // Create a map of table names to table data
            Map<String, Table> tables = new HashMap<>();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the data for each table and add it to the map
            ResultSet tablesInfo = connection.getMetaData().getTables(null, null, null,  null);
            while (tablesInfo.next()) {
                String tableName = tablesInfo.getString("TABLE_NAME");
                tables.put(tableName, this.getTable(tableName, statement));
            }

            return tables;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Creates a Table with the data from the database using the given table name using the given connection statement.
     * @param name Name of the table to retrieve
     * @param statement Connection statement to use to retrieve data
     * @return Table of the data
     */
    private Table getTable(String name, Statement statement) {
        try {
            ResultSet rs = statement.executeQuery("select * from " + name);

            // Get the set of column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            Set<String> colNames = new HashSet<>();
            for (int i = 1; i <= numCols; i++) {
                colNames.add(rsmd.getColumnName(i));
            }

            Table table = new Table(colNames);

            // Make an entry in the map for each column and its value
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (String n : colNames) {
                    row.put(n, rs.getString(n));
                }
                table.addRow(row);
            }

            return table;
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

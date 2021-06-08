package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a table with rows and columns.
 * Rows can be added to or deleted from the table.
 */
public class Table {

    private final Set<String> columnNames;
    private List<Map<String, String>> rows;

    /**
     * Creates an empty table with the given column names.
     * @param columnNames Column names.
     */
    public Table(Set<String> columnNames) {
        this.columnNames = columnNames;
        this.rows = new ArrayList<>();
    }

    /**
     * Returns a list of all the rows in the table.
     * @return List of all the rows in the table.
     */
    public List<Map<String, String>> getAllRows() {
        return this.rows;
    }

    /**
     * Returns the row at the given row number (First row is 0).
     * @param row Row number to retrieve.
     * @return Row at given row number.
     */
    public Map<String, String> getRow(int row) {
        return this.rows.get(row);
    }

    /**
     * Returns the names of the columns.
     * @return Names of the columns.
     */
    public Set<String> getColumnNames() {
        return this.columnNames;
    }

    /**
     * Adds the given row to the table.
     * (Does nothing if the column names do not match).
     * @param row Row to be added.
     */
    public void addRow(Map<String, String> row) {
        if (columnNamesMatch(this.columnNames, row.keySet())) {
            this.rows.add(row);
        }
    }

    /**
     * Deletes the given row from the table if it exists.
     * @param row Row to be deleted.
     */
    public void deleteRow(Map<String, String> row) {
        if (columnNamesMatch(this.columnNames, row.keySet())) {
            this.rows.removeIf(r -> rowsMatch(r, row));
        }
    }

    /**
     * Checks if the two rows match.
     * (column names match, and all the values of the entries for each column match).
     * @param a First row.
     * @param b Second row.
     * @return Whether or not the two rows match.
     */
    private boolean rowsMatch(Map<String, String> a, Map<String, String> b) {
        for (String c : a.keySet()) {
            if (!a.get(c).equals(b.get(c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the two sets of column names match.
     * (No one set has any column that doesn't exist in the other set).
     * @param a First set of column names.
     * @param b Second set of column names.
     * @return Whether or not the two sets of column names match.
     */
    private boolean columnNamesMatch(Set<String> a, Set<String> b) {
        for (String c : a) {
            if (!b.contains(a)) {
                return false;
            }
        }
        for (String c : b) {
            if (!a.contains(b)) {
                return false;
            }
        }
        return true;
    }
}

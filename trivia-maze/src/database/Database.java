package database;

import java.util.List;
import java.util.Map;

public interface Database {
    Map<String, Table> getData();
    void close();
}

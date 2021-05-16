package database;

import java.util.List;
import java.util.Map;

public interface Database {
    Map<String, List<Map<String, String>>> getData();
    void close();
}

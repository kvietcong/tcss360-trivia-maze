package database;

import java.util.List;
import java.util.Map;

public interface Database {
    // TODO: Review interface implementation
    List<Map<String, String>> getData();
    void close();
}

package maze;

import java.io.Serializable;
import java.util.Set;

public interface Maze extends Iterable<Room>, Serializable {
    Set<Room> getRooms();
    Set<Room> getNeighbors(Room x);
}

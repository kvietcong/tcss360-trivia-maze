package maze;

import java.util.Set;

public interface Maze {
    Set<Room> getRooms();
    Set<Room> getNeighbors(Room x);
}

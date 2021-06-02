package maze;

import java.io.Serializable;
import java.util.Set;

public interface Maze extends Iterable<Room>, Serializable {
    /** @return Retrieve all the rooms of the maze. */
    Set<Room> getRooms();

    /**
     * Get the neighbors of a given room.
     * @param room The given room to check for.
     * @return The neighbors of a given room.
     */
    Set<Room> getNeighbors(Room room);
}

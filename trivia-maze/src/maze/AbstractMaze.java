package maze;

import java.util.Iterator;
import java.util.Set;

public abstract class AbstractMaze implements Maze {
    public abstract Set<Room> getRooms();
    public abstract Set<Room> getNeighbors(Room current);
    public Iterator<Room> iterator() { return getRooms().iterator(); }
}

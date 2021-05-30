package maze;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MazeGraph extends AbstractMaze {
    /** The maze in graph form */
    private final Map<Room, Set<Room>> maze;

    /**
     * Construct a new maze object.
     * @param maze The new maze.
     */
    public MazeGraph(Map<Room, Set<Room>> maze) { this.maze = maze; }

    @Override
    public Set<Room> getRooms() { return maze.keySet(); }

    @Override
    public Set<Room> getNeighbors(Room room) { return maze.getOrDefault(room, new HashSet<>()); }
}

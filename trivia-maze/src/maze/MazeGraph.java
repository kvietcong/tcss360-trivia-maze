package maze;

import java.util.*;

public class MazeGraph extends AbstractMaze {
    private final Map<Room, Set<Room>> maze;

    public MazeGraph(Map<Room, Set<Room>> maze) { this.maze = maze; }

    @Override
    public Set<Room> getRooms() { return maze.keySet(); }

    @Override
    public Set<Room> getNeighbors(Room current) { return maze.getOrDefault(current, new HashSet<>()); }
}
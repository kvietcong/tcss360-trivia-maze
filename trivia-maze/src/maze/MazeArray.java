package maze;

import java.util.*;
import java.util.stream.Stream;

public class MazeArray extends AbstractMaze {
    private final Room[][] maze;

    public MazeArray(Room[][] maze) { this.maze = maze; }

    @Override
    public Set<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (Room[] room : maze) {
            rooms.addAll(Arrays.asList(room));
        }
        return new HashSet<>(rooms);
    }

    @Override
    public Set<Room> getNeighbors(Room room) {
        Set<Room> neighbors = new HashSet<>();
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                Room current = maze[x][y];
                if (current.equals(room)) {
                    for (int xi = x - 1; xi <= x + 1; xi++) {
                        for (int yi = y - 1; yi <= y + 1; y++) {
                            if ((xi != x || yi != y)
                                    && xi > 0 && xi < maze.length
                                    && yi > 0 && yi < maze[x].length
                            ) {
                                neighbors.add(maze[xi][yi]);
                            }
                        }
                    }
                    return neighbors;
                }
            }
        }
        return neighbors;
    }
}

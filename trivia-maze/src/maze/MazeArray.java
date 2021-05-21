package maze;

import java.util.*;

public class MazeArray extends AbstractMaze {
    /** The maze in graph form */
    private final Room[][] rooms;

    /**
     * Construct a new maze object.
     * @param maze The new maze.
     */
    public MazeArray(Room[][] maze) { this.rooms = maze; }

    @Override
    public Set<Room> getRooms() {
        return new HashSet<>(Arrays.stream(rooms).flatMap(Arrays::stream).toList());
    }

    @Override
    public Set<Room> getNeighbors(Room room) {
        Set<Room> neighbors = new HashSet<>();
        for (int x = 0; x < rooms.length; x++) {
            for (int y = 0; y < rooms[x].length; y++) {
                Room current = rooms[x][y];
                if (current.equals(room)) {
                    for (int xi = x - 1; xi <= x + 1; xi++) {
                        for (int yi = y - 1; yi <= y + 1; y++) {
                            if ((xi != x || yi != y)
                                    && xi > 0 && xi < rooms.length
                                    && yi > 0 && yi < rooms[x].length
                            ) {
                                neighbors.add(rooms[xi][yi]);
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
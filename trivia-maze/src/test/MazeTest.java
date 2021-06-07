package test;

import maze.Maze;
import maze.MazeGraph;
import maze.Room;
import maze.RoomSimple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.GameStateSimple;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MazeTest {
    /** Testing maze. */
    private Maze testMaze;

    @BeforeEach
    void setUp() {
        test.Test.main(null); // Regenerate test.maze

        Room startRoom = new RoomSimple(0);
        Room endRoom = new RoomSimple(1);

        Map<Room, Set<Room>> testMazeMap = new HashMap<>();
        testMazeMap.put(startRoom, Collections.singleton(endRoom));
        testMazeMap.put(endRoom, Collections.singleton(startRoom));
        testMaze = new MazeGraph(testMazeMap);
    }

    @Test
    void getRooms() {
        assertAll("Testing how the maze retrieves all its rooms",
                () -> assertEquals(
                        testMaze.getRooms(),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(0), new RoomSimple(1)
                        )),
                        "Failed to get the correct rooms"
                ),
                () -> assertEquals(
                        testMaze.getRooms().size(), 2,
                        "Failed to get the correct amount of rooms"
                ),

                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze().getRooms(),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(0), new RoomSimple(1),
                                new RoomSimple(2), new RoomSimple(3),
                                new RoomSimple(4), new RoomSimple(5),
                                new RoomSimple(6), new RoomSimple(7),
                                new RoomSimple(8), new RoomSimple(9)
                        )),
                        "Failed to get the correct rooms"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze().getRooms().size(), 10,
                        "Failed to get the correct amount of rooms"
                )
        );
    }

    @Test
    void getNeighbors() {
        assertAll("Testing how the maze retrieves neighbors",
                () -> assertEquals(
                        testMaze.getNeighbors(new RoomSimple(0)),
                        new HashSet<>(Collections.singletonList(new RoomSimple(1))),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        testMaze.getNeighbors(new RoomSimple(1)),
                        new HashSet<>(Collections.singletonList(new RoomSimple(0))),
                        "Failed to get the correct neighbors"
                ),

                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(0)),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(1), new RoomSimple(2)
                        )),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(1)),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(0),
                                new RoomSimple(3),
                                new RoomSimple(4)
                        )),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(2)),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(0),
                                new RoomSimple(4),
                                new RoomSimple(5)
                        )),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(4)),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(1),
                                new RoomSimple(2),
                                new RoomSimple(6),
                                new RoomSimple(7)
                        )),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(8)),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(6),
                                new RoomSimple(7),
                                new RoomSimple(9)
                        )),
                        "Failed to get the correct neighbors"
                ),
                () -> assertEquals(
                        GameStateSimple.getInstance().getMaze()
                                .getNeighbors(new RoomSimple(9)),
                        new HashSet<>(Collections.singletonList(new RoomSimple(8))),
                        "Failed to get the correct neighbors"
                )
        );
    }
}
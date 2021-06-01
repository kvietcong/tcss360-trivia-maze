package test;

import maze.Maze;
import maze.MazeGraph;
import maze.Room;
import maze.RoomSimple;
import question.Question;
import question.QuestionTF;
import state.GameState;
import state.GameStateSimple;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test Game State interface.
 */
class GameStateTest {
    /** Testing State. */
    GameState testState;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Room startRoom = new RoomSimple(0);
        Room endRoom = new RoomSimple(1);

        Map<Room, Set<Room>> testMazeMap = new HashMap<>();
        testMazeMap.put(startRoom, Collections.singleton(endRoom));
        testMazeMap.put(endRoom, Collections.singleton(startRoom));

        Map<Room, GameState.RoomState> testStateMap = new HashMap<>();
        testStateMap.put(startRoom, GameState.RoomState.UNLOCKED);
        testStateMap.put(endRoom, GameState.RoomState.UNKNOWN);

        Question testQuestion = new QuestionTF("Test", new String[0], "Answer");
        Map<Room, Question> testQuestions = new HashMap<>();
        testQuestions.put(startRoom, testQuestion);
        testQuestions.put(endRoom, testQuestion);

        GameStateSimple manualStateSimple = (GameStateSimple) GameStateSimple.getInstance();
        Maze testMaze2 = new MazeGraph(testMazeMap);

        manualStateSimple.setState(testMaze2, startRoom, endRoom, startRoom, testStateMap, testQuestions);
        testState = manualStateSimple;
    }

    @org.junit.jupiter.api.Test
    void initiateState() {
        testState.initiateState();
        assertAll("Test game initialization",
                () -> assertNotNull(testState.getCurrentRoom(), "Game state not initialized"),
                () -> assertNotNull(testState.getQuestions(), "Game state not initialized"),
                () -> assertNotNull(testState.getQuestions(), "Game state not initialized"),
                () -> assertNotNull(testState.getMaze(), "Game state not initialized"),
                () -> assertNotNull(testState.getStartRoom(), "Game state not initialized"),
                () -> assertNotNull(testState.getEndRoom(), "Game state not initialized"),
                () -> assertNotNull(testState.getCurrentNeighbors(), "Game state not initialized")
        );
    }

    @org.junit.jupiter.api.Test
    void loadState() {
        testState.loadState("./test.maze");
        Room startRoom = new RoomSimple(0);
        Room endRoom = new RoomSimple(9);
        assertAll("Test how state loads from a file",
                () -> assertEquals(testState.getQuestions().size(), 10, "Wrong amount of questions"),
                () -> assertEquals(testState.getMaze().getRooms().size(), 10, "Wrong amount of rooms"),
                () -> assertEquals(testState.getRoomState(startRoom),
                        GameState.RoomState.UNLOCKED, "Start is not unlocked"),
                () -> assertEquals(testState.getRoomState(endRoom),
                        GameState.RoomState.UNKNOWN, "End is not unknown")
        );
    }

    @org.junit.jupiter.api.Test
    void saveState() {
        class Dummy implements PropertyChangeListener {
            public boolean isSaved = false;
            public Dummy() {
                testState.addPropertyChangeListener(this);
                testState.saveState("./testSaving.maze");
            }

            @Override
            public void propertyChange(PropertyChangeEvent event) {
                isSaved = event.getPropertyName().equals(GameState.GameEvent.SAVE.name());
            }
        }

        Dummy dummy = new Dummy();
        assertAll("Test Saving",
                () -> assertTrue(dummy.isSaved, "Failed to save"),
                () -> assertTrue((new File("./testSaving.maze")).exists(), "Failed to find save"),
                () -> assertTrue((new File("./testSaving.maze")).delete(), "Failed to delete save")
        );
    }

    @org.junit.jupiter.api.Test
    void getMaze() {
        Room startRoom = new RoomSimple(0);
        Room endRoom = new RoomSimple(1);

        assertAll("Test how state retrieves maze",
                () -> assertEquals(testState.getMaze().getRooms().size(), 2, "Wrong amount of rooms"),
                () -> assertEquals(testState.getMaze().getNeighbors(startRoom),
                        Collections.singleton(endRoom), "Wrong Neighbor"),
                () -> assertEquals(testState.getMaze().getNeighbors(endRoom),
                        Collections.singleton(startRoom), "Wrong Neighbor")
        );
    }

    @org.junit.jupiter.api.Test
    void getQuestions() {
        Question testQuestion = new QuestionTF("Test", new String[0], "Answer");
        assertAll("Test how state retrieves the questions",
                () -> assertEquals(testState.getQuestions().get(new RoomSimple(0)),
                        testQuestion, "Wrong question"),
                () -> assertEquals(testState.getQuestions().get(new RoomSimple(1)),
                        testQuestion, "Wrong question"),
                () -> assertNull(testState.getQuestions().get(new RoomSimple(9)),
                        "Should be null for nonexistent room")
        );
    }

    @org.junit.jupiter.api.Test
    void getRoomQuestion() {
        Question testQuestion = new QuestionTF("Test", new String[0], "Answer");
        assertAll("Test how state retrieves a room's questions",
                () -> assertEquals(testState.getRoomQuestion(new RoomSimple(0)),
                        testQuestion, "Wrong question"),
                () -> assertEquals(testState.getRoomQuestion(new RoomSimple(1)),
                        testQuestion, "Wrong question"),
                () -> assertNull(testState.getRoomQuestion(new RoomSimple(9)),
                        "Should be null for nonexistent room")
        );
    }

    @org.junit.jupiter.api.Test
    void getCurrentRoom() {
        assertAll("Test how state retrieves the current room",
                () -> assertEquals(testState.getCurrentRoom(), new RoomSimple(0), "Wrong room")
        );

        testState.loadState("./test.maze");
        assertAll("Test how state retrieves the current room",
                () -> assertEquals(testState.getCurrentRoom(), new RoomSimple(0), "Wrong room")
        );
    }

    @org.junit.jupiter.api.Test
    void moveToRoom() {
        testState.moveToRoom(new RoomSimple(1));
        assertAll("Test how state moves to a room",
                () -> assertEquals(testState.getCurrentRoom(), new RoomSimple(1), "Wrong room")
        );

        testState.loadState("./test.maze");
        testState.moveToRoom(new RoomSimple(5));
        assertAll("Test how state moves to a room",
                () -> assertEquals(testState.getCurrentRoom(), new RoomSimple(5), "Wrong room")
        );
    }

    @org.junit.jupiter.api.Test
    void getCurrentNeighbors() {
        assertAll("Test how state moves to a room",
                () -> assertEquals(testState.getCurrentNeighbors(),
                        Collections.singleton(new RoomSimple(1)), "Wrong Neighbors"),
                () -> assertNotEquals(testState.getCurrentNeighbors(),
                        new HashSet<>(Arrays.asList(new RoomSimple(9), new RoomSimple(1))),
                        "Wrong Neighbors")
        );

        testState.loadState("./test.maze");
        testState.moveToRoom(new RoomSimple(4));
        assertAll("Test how state moves to a room",
                () -> assertEquals(testState.getCurrentNeighbors(),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(1),
                                new RoomSimple(2),
                                new RoomSimple(6),
                                new RoomSimple(7))), "Wrong Neighbors"),
                () -> assertNotEquals(testState.getCurrentNeighbors(),
                        new HashSet<>(Arrays.asList(
                                new RoomSimple(0),
                                new RoomSimple(9),
                                new RoomSimple(8),
                                new RoomSimple(1))), "Wrong Neighbors")
        );
    }

    @org.junit.jupiter.api.Test
    void getDistanceToEnd() {
        testState.loadState("./test.maze");
        assertAll("Test how distances are calculated",
                () -> assertEquals(testState.getDistanceToEnd(testState.getStartRoom()),
                        5, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(testState.getEndRoom()),
                        0, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(1)),
                        4, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(2)),
                        4, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(3)),
                        3, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(4)),
                        3, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(5)),
                        3, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(6)),
                        2, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(7)),
                        2, "Wrong Distance"),
                () -> assertEquals(testState.getDistanceToEnd(new RoomSimple(8)),
                        1, "Wrong Distance")
        );
    }

    @org.junit.jupiter.api.Test
    void checkRoomState() {
        assertAll("Test how room state is returned",
                () -> assertEquals(testState.getRoomState(new RoomSimple(0)),
                        GameState.RoomState.UNLOCKED, "Wrong room state returned"),
                () -> assertEquals(testState.getRoomState(new RoomSimple(1)),
                        GameState.RoomState.UNKNOWN, "Wrong room state returned")
        );
        testState.setRoomState(new RoomSimple(1), GameState.RoomState.LOCKED);
        assertAll("Test how room state is returned",
                () -> assertEquals(testState.getRoomState(new RoomSimple(0)),
                        GameState.RoomState.UNLOCKED, "Wrong room state returned"),
                () -> assertEquals(testState.getRoomState(new RoomSimple(1)),
                        GameState.RoomState.LOCKED, "Wrong room state returned")
        );

        testState.loadState("./test.maze");
        assertAll("Test how room state is returned",
                () -> assertEquals(testState.getRoomState(new RoomSimple(0)),
                        GameState.RoomState.UNLOCKED, "Wrong room state returned")
        );
    }

    @org.junit.jupiter.api.Test
    void setRoomState() {
        assertAll("Test how room state is set",
                () -> assertEquals(testState.getRoomState(new RoomSimple(0)),
                        GameState.RoomState.UNLOCKED, "Wrong room state returned"),
                () -> assertEquals(testState.getRoomState(new RoomSimple(1)),
                        GameState.RoomState.UNKNOWN, "Wrong room state returned")
        );
        testState.setRoomState(new RoomSimple(1), GameState.RoomState.LOCKED);
        assertAll("Test how room state is set",
                () -> assertEquals(testState.getRoomState(new RoomSimple(0)),
                        GameState.RoomState.UNLOCKED, "Wrong room state returned"),
                () -> assertEquals(testState.getRoomState(new RoomSimple(1)),
                        GameState.RoomState.LOCKED, "Wrong room state returned")
        );
    }
}
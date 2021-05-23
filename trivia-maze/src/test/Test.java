package test;

import maze.Maze;
import maze.MazeGraph;
import maze.Room;
import maze.RoomSimple;
import question.Question;
import question.QuestionTF;
import state.GameState;
import state.GameStateSimple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Room room0 = new RoomSimple(0);
        Room room1 = new RoomSimple(1);
        Room room2 = new RoomSimple(2);
        Room room3 = new RoomSimple(3);
        Room room4 = new RoomSimple(4);
        Room room5 = new RoomSimple(5);
        Room room6 = new RoomSimple(6);
        Room room7 = new RoomSimple(7);
        Room room8 = new RoomSimple(8);
        Room room9 = new RoomSimple(9);

        Set<Room> rooms0 = new HashSet<>();
        rooms0.add(room1);
        rooms0.add(room2);

        Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room0);
        rooms1.add(room3);
        rooms1.add(room4);

        Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room0);
        rooms2.add(room4);
        rooms2.add(room5);

        Set<Room> rooms3 = new HashSet<>();
        rooms3.add(room1);
        rooms3.add(room6);

        Set<Room> rooms4 = new HashSet<>();
        rooms4.add(room1);
        rooms4.add(room2);
        rooms4.add(room6);
        rooms4.add(room7);

        Set<Room> rooms5 = new HashSet<>();
        rooms5.add(room2);
        rooms5.add(room7);

        Set<Room> rooms6 = new HashSet<>();
        rooms6.add(room3);
        rooms6.add(room4);
        rooms6.add(room8);

        Set<Room> rooms7 = new HashSet<>();
        rooms7.add(room4);
        rooms7.add(room5);
        rooms7.add(room8);

        Set<Room> rooms8 = new HashSet<>();
        rooms8.add(room6);
        rooms8.add(room7);
        rooms8.add(room9);

        Set<Room> rooms9 = new HashSet<>();
        rooms9.add(room8);

        Map<Room, Set<Room>> rooms = new HashMap<>();
        rooms.put(room0, rooms0);
        rooms.put(room1, rooms1);
        rooms.put(room2, rooms2);
        rooms.put(room3, rooms3);
        rooms.put(room4, rooms4);
        rooms.put(room5, rooms5);
        rooms.put(room6, rooms6);
        rooms.put(room7, rooms7);
        rooms.put(room8, rooms8);
        rooms.put(room9, rooms9);

        Maze maze = new MazeGraph(rooms);

        Question testQ = new QuestionTF("You are ok.", new String[] { "you", "health" }, "false");
        Map<Room, Question> questions = new HashMap<>();
        maze.forEach(room -> questions.put(room, testQ));

        Map<Room, GameState.RoomState> roomStates = new HashMap<>();
        maze.forEach(room -> roomStates.put(room, GameState.RoomState.UNKNOWN));

        GameState state = GameStateSimple.getInstance();
        GameStateSimple gameState = (GameStateSimple) state;
        gameState.setState(maze, room0, room9, room0, roomStates, questions);
        state.setRoomState(room0, GameState.RoomState.UNLOCKED);

        gameState.getMaze().getRooms().forEach(room ->
                System.out.println(room + ": " + gameState.getDistanceToEnd(room) + " units away from the end"));
    }
}

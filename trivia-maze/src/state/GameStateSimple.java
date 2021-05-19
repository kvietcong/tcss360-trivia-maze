package state;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import maze.Maze;
import maze.Room;
import question.Question;

import static state.GameState.GameEvent.*;
import static state.GameState.RoomState.*;

public class GameStateSimple implements GameState {
    private static final GameState STATE = new GameStateSimple();

    private final PropertyChangeSupport propertyChangeSupport;

    private Maze maze;
    private Room endRoom;
    private Room startRoom;
    private Room currentRoom;
    private Map<Room, Question> questions;
    private Map<Room, RoomState> roomStates;
    private Map<Room, Integer> distancesToEndRoom;

    private GameStateSimple() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public static GameState getInstance() { return STATE; }

    public void loadState(String loadInfo) {
        calculatePaths();
    }

    public Maze getMaze() { return maze; }
    public Room getEndRoom() { return endRoom; }
    public Room getStartRoom() { return startRoom; }
    public Room getCurrentRoom() { return currentRoom; }
    public Map<Room, Question> getQuestions() { return questions; }
    public int getDistanceToEnd(Room room) { return distancesToEndRoom.getOrDefault(room, -1); }

    // TODO: Saving
    public void saveState(String saveInfo) { }
    // TODO: Preload or Generate a new Maze
    public void initiateState() { }


    public Question getRoomQuestion(Room room) { return questions.getOrDefault(room, null); }

    public void moveToRoom(Room newRoom) {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        propertyChangeSupport.firePropertyChange(MOVE.toString(), oldRoom, newRoom);
    }

    public Set<Room> getNeighbors(Room room) { return maze.getNeighbors(room); }
    public Set<Room> getCurrentNeighbors() { return getNeighbors(currentRoom); }

    public RoomState checkRoomState(Room room) {
        return roomStates.getOrDefault(room, UNKNOWN);
    }

    public void setRoomState(Room room, RoomState state) {
        roomStates.put(room, state);
    }

    private void calculatePaths() {
        // OH GOODNESS THE TIME COMPLEXITY!!!!!!!!!!!!
        distancesToEndRoom = new HashMap<>();
        distancesToEndRoom.put(endRoom, 0);

        maze.getRooms().stream().filter(room -> !room.equals(endRoom)).forEach(room -> {
            Map<Room, Integer> distanceTo = new HashMap<>();
            Queue<Room> toSearch = new LinkedList<>();
            Set<Room> searched = new HashSet<>();

            toSearch.add(room);
            searched.add(room);
            distanceTo.put(room, 0);

            while (!toSearch.isEmpty()) {
                Room currentRoom = toSearch.poll();
                Set<Room> neighbors = getNeighbors(currentRoom);
                for (Room neighbor : neighbors) {
                    if (!searched.contains(neighbor)) {
                        toSearch.add(neighbor);
                        searched.add(neighbor);
                        distanceTo.put(neighbor, distanceTo.getOrDefault(currentRoom, 0) + 1);
                        if (neighbor.equals(endRoom)) { break; }
                    }
                }
            }
            distancesToEndRoom.put(room, distanceTo.getOrDefault(endRoom, -1));
        });
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(final GameEvent event,
                                          final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(event.toString(), listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final GameEvent event,
                                             final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(event.toString(), listener);
    }

    // TODO: BELOW HERE IS FOR TESTING. DELETE WHEN DONE
    public void setState(Maze maze, Room startRoom, Room endRoom, Room currentRoom,
                         Map<Room, RoomState> roomStates, Map<Room, Question> questions) {
        this.maze = maze;
        this.endRoom = endRoom;
        this.startRoom = startRoom;
        this.questions = questions;
        this.roomStates = roomStates;
        this.currentRoom = currentRoom;

        calculatePaths();
    }
}

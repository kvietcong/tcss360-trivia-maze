package state;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.Set;

import maze.Maze;
import maze.Room;
import question.Question;

import static state.GameState.GameEvent.*;

public class GameStateSimple implements GameState {
    private static final GameState STATE = new GameStateSimple();

    private final PropertyChangeSupport propertyChangeSupport;

    private Maze maze;
    private Room currentRoom;
    private Set<Room> unlockedRooms;
    private Map<Room, Question> questions;

    private GameStateSimple() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public static GameState getInstance() { return STATE; }

    public void loadState(Maze maze,
                          Map<Room, Question> questions,
                          Set<Room> unlockedRooms,
                          Room currentRoom) {
        this.maze = maze;
        this.questions = questions;
        this.unlockedRooms = unlockedRooms;
        this.currentRoom = currentRoom;
    }

    public void loadState(String loadInfo) { }

    // TODO: Saving
    public void saveState(String saveInfo) { }

    // TODO: Preload or Generate a new Maze
    public void initiateState() { }

    public Maze getMaze() { return maze; }

    public Map<Room, Question> getQuestions() { return questions; }

    public Room getCurrentRoom() { return currentRoom; }

    public Question getQuestion(Room room) { return questions.getOrDefault(room, null); }

    public void moveToRoom(Room newRoom) {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        propertyChangeSupport.firePropertyChange(MOVE.toString(), oldRoom, newRoom);
    }

    public Set<Room> getCurrentNeighbors() {
        return maze.getNeighbors(currentRoom);
    }

    public Set<Room> getUnlockedRooms() { return unlockedRooms; }

    public boolean isUnlocked(Room room) { return unlockedRooms.contains(room); }

    public void unlockRoom(Room room) { unlockedRooms.add(room); }

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
}

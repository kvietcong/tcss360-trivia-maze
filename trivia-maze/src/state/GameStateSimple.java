package state;

import database.TriviaBase;
import database.TriviaDatabaseConnection;
import constants.C;
import maze.Maze;
import maze.MazeGraph;
import maze.MazeReader;
import maze.Room;
import maze.RoomSimple;
import question.Question;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static state.GameState.GameEvent.LOAD;
import static state.GameState.GameEvent.LOSE;
import static state.GameState.GameEvent.MOVE;
import static state.GameState.GameEvent.ROOM_CHANGE;
import static state.GameState.GameEvent.SAVE;
import static state.GameState.GameEvent.WIN;

public final class GameStateSimple implements GameState {
    /** Singleton instance of the game state. */
    private static final transient GameState STATE = new GameStateSimple();

    /** Object to handle the event firing and listening. */
    private final transient PropertyChangeSupport propertyChangeSupport;

    /** Stores any clips played during the game. */
    private final transient Set<Clip> audioClips = new HashSet<>();

    /** The state's game maze. */
    private Maze maze;
    /** The end (finishing) room. */
    private Room endRoom;
    /** The room where a new player starts. */
    private Room startRoom;
    /** The room where the player is currently residing. */
    private Room currentRoom;
    /** All the questions and the associated room. */
    private Map<Room, Question> questions;
    /** All the room states and the associated room. */
    private Map<Room, RoomState> roomStates;
    /** All room distances from every room to the end. */
    private Map<Room, Integer> distancesToEndRoom;

    /** Initializes a new Game State Object with event firing support. */
    private GameStateSimple() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void initiateState() {
        this.maze = new MazeGraph(MazeReader.readMaze("./resources/maze-1.txt"));
        this.questions = new HashMap<>();
        this.roomStates = new HashMap<>();
        // Room [id = 0] --> Start
        // Room [id = {size - 1}] --> End
        Room start = new RoomSimple(0);
        this.startRoom = start;
        this.currentRoom = start;
        this.endRoom = new RoomSimple(this.maze.getRooms().size() - 1);

        TriviaBase triviaBase = new TriviaBase(TriviaDatabaseConnection.getConnection());
        this.maze.forEach(room -> questions.put(room, triviaBase.getRandomQuestion()));
        this.maze.forEach(room -> this.roomStates.put(room, RoomState.UNKNOWN));
        this.roomStates.put(start, RoomState.UNLOCKED);

        calculatePaths();

        propertyChangeSupport.firePropertyChange(LOAD.name(), null, this);
        clearSound();
    }

    public boolean saveState(String savePath) {
        try (FileOutputStream fileOut = new FileOutputStream(savePath)) {
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(this);
                propertyChangeSupport.firePropertyChange(
                        SAVE.name(), null, this);
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean loadState(String loadPath) {
        try (FileInputStream fileIn = new FileInputStream(loadPath)) {
            try (ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                setState((GameStateSimple) objectIn.readObject());
                calculatePaths();
                clearSound();
                propertyChangeSupport.firePropertyChange(LOAD.name(), null, this);
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public Maze getMaze() { return maze; }
    public Room getEndRoom() { return endRoom; }
    public Room getStartRoom() { return startRoom; }
    public Room getCurrentRoom() { return currentRoom; }
    public static GameState getInstance() { return STATE; }
    public Map<Room, Question> getQuestions() { return questions; }
    public Set<Room> getNeighbors(Room room) { return maze.getNeighbors(room); }
    public Set<Room> getCurrentNeighbors() { return getNeighbors(currentRoom); }
    public RoomState getRoomState(Room room) { return roomStates.getOrDefault(room, null); }
    public Question getRoomQuestion(Room room) { return questions.getOrDefault(room, null); }
    public int getDistanceToEnd(Room room) { return distancesToEndRoom.getOrDefault(room, -1); }

    public int getProgress() {
        int max = -1;
        for (Room room : maze) {
            max = Math.max(max, getDistanceToEnd(room));
        }
        return 100 - (int) ((float) getDistanceToEnd(currentRoom) / max * 100);
    }

    public void setRoomState(Room room, RoomState newState) {
        RoomState oldState = getRoomState(room);
        roomStates.put(room, newState);
        propertyChangeSupport.firePropertyChange(ROOM_CHANGE.name(), oldState, newState);
        calculatePaths();
    }

    public void attemptQuestion(Room room, String answer) {
        Question question = getRoomQuestion(room);
        if (question.isCorrectAnswer(answer)) {
            playFile("resources/correct.wav");
            setRoomState(room, RoomState.UNLOCKED);
        } else {
            playFile("resources/incorrect.wav");
            if (room.equals(endRoom)) { return; } // Make it so you can't lock the final room
            setRoomState(room, RoomState.LOCKED);
        }
    }

    public void moveToRoom(Room newRoom) {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        propertyChangeSupport.firePropertyChange(MOVE.name(), oldRoom, newRoom);
        if (currentRoom.equals(endRoom)) {
            playFile("resources/come on and slam.wav");
            propertyChangeSupport.firePropertyChange(WIN.name(), oldRoom, newRoom);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Makes the current game state emulate the new one.
     * (The current game state won't inherit things like the singleton
     *  instance or the property change listener.)
     * @param newState New State to emulate.
     */
    private void setState(GameStateSimple newState) {
        maze = newState.maze;
        endRoom = newState.endRoom;
        startRoom = newState.startRoom;
        currentRoom = newState.currentRoom;
        questions = newState.questions;
        roomStates = newState.roomStates;
        distancesToEndRoom = newState.distancesToEndRoom;
        propertyChangeSupport.firePropertyChange(
                LOAD.name(), null, this);
    }

    /**
     * Calculate every room's distance to the final room.
     */
    private void calculatePaths() {
        if (maze == null) { return; }
        distancesToEndRoom = new HashMap<>();

        Map<Room, Set<Room>> reversedMaze = new HashMap<>();
        maze.getRooms().forEach(room -> {
            Set<Room> neighbors = getNeighbors(room);
            neighbors.forEach(neighbor -> {
                Set<Room> reversedNeighbors = reversedMaze.getOrDefault(neighbor, new HashSet<>());
                reversedNeighbors.add(room);
                reversedMaze.put(neighbor, reversedNeighbors);
            });
        });

        Queue<Room> toSearch = new LinkedList<>();
        Set<Room> searched = new HashSet<>();

        toSearch.add(endRoom);
        searched.add(endRoom);
        distancesToEndRoom.put(endRoom, 0);

        while (!toSearch.isEmpty()) {
            Room current = toSearch.poll();
            Set<Room> neighbors = reversedMaze.get(current);
            for (Room neighbor : neighbors) {
                if (!searched.contains(neighbor)
                        && getRoomState(neighbor) != RoomState.LOCKED) {
                    toSearch.add(neighbor);
                    searched.add(neighbor);
                    distancesToEndRoom.put(
                            neighbor,
                            distancesToEndRoom.get(current) + 1);
                    if (neighbor.equals(endRoom)) { break; }
                }
            }
        }

        if (getDistanceToEnd(getCurrentRoom()) == -1) {
            playFile("resources/sad violin.wav");
            propertyChangeSupport.firePropertyChange(LOSE.name(), null, this);
        }
    }

    /**
     * Play a given sound file.
     * @param file File to play.
     */
    private void playFile(String file) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            ((FloatControl)
                    clip.getControl(FloatControl.Type.MASTER_GAIN)
            ).setValue(C.GAIN);
            clip.start();
            audioClips.add(clip);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Failed to load audio");
        }
    }

    /**
     * Reset all the sound clips.
     */
    private void clearSound() {
        audioClips.forEach(DataLine::stop);
        audioClips.clear();
    }

    // TODO: BELOW HERE IS FOR TESTING. DELETE WHEN DONE
    /**
     * DEBUG FUNCTION: Set the state of the maze manually.
     * @param maze The maze to be set.
     * @param startRoom The starting room.
     * @param endRoom The ending room.
     * @param currentRoom The current state's room.
     * @param roomStates The states the rooms are in.
     * @param questions The questions that are attached to the rooms.
     */
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

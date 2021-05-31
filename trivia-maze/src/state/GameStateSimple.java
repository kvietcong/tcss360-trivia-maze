package state;

import maze.Maze;
import maze.Room;
import question.Question;

import javax.sound.sampled.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;

import static state.GameState.GameEvent.*;

public class GameStateSimple implements GameState {
    /** Singleton instance of the game state */
    final transient private static GameState STATE = new GameStateSimple();

    /** Object to handle the event firing and listening. */
    final transient private PropertyChangeSupport propertyChangeSupport;

    /** Stores any clips played during the game */
    final transient private Set<Clip> audioClips = new HashSet<>();

    /** The state's game maze */
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

    public void setRoomState(Room room, RoomState newState) {
        RoomState oldState = getRoomState(room);
        roomStates.put(room, newState);
        propertyChangeSupport.firePropertyChange(ROOM_CHANGE.name(), oldState, newState);
        calculatePaths();
    }

    public void initiateState() {
        // TODO: Remove this when initiateState is implemented
        loadState("./test.maze");
        // TODO: Remove this when initiateState is implemented

        calculatePaths();
        clearSound();
        propertyChangeSupport.firePropertyChange(LOAD.name(), null, this);
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
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        clearSound();
        return true;
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

    public void moveToRoom(Room newRoom) {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        propertyChangeSupport.firePropertyChange(MOVE.name(), oldRoom, newRoom);
        if (currentRoom == endRoom) {
            playFile("resources/come on and slam.wav");
            propertyChangeSupport.firePropertyChange(WIN.name(), oldRoom, newRoom);
        }
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
            Room currentRoom = toSearch.poll();
            Set<Room> neighbors = reversedMaze.get(currentRoom);
            for (Room neighbor : neighbors) {
                if (!searched.contains(neighbor)
                        && getRoomState(neighbor) != RoomState.LOCKED) {
                    toSearch.add(neighbor);
                    searched.add(neighbor);
                    distancesToEndRoom.put(neighbor, distancesToEndRoom.get(currentRoom) + 1);
                    if (neighbor.equals(endRoom)) { break; }
                }
            }
        }

        if (getDistanceToEnd(getCurrentRoom()) == -1) {
            playFile("resources/sad violin.wav");
            propertyChangeSupport.firePropertyChange(LOSE.name(), null, this);
        }

        /* OH GOODNESS THE TIME COMPLEXITY!!!!!!!!!!!!
         * So this code below here goes through every node in the graph and does BFS on EACH ONE
         * to find the distance to the end. This means the time complexity is O(V^2 + VE) where
         * V is the amount of vertices and E is the amount of Edges
         * I will keep this as a remainder of my failure
         * - KV Le
         */
        /*
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
                        distanceTo.put(neighbor, distanceTo.get(currentRoom) + 1);
                        if (neighbor.equals(endRoom)) { break; }
                    }
                }
            }
            distancesToEndRoom.put(room, distanceTo.getOrDefault(endRoom, -1));
        });
        distancesToEndRoom.put(endRoom, 0);
        */
    }

    /**
     * Play a given sound file.
     * @param file File to play.
     */
    private void playFile(String file) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-20.0f);
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

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
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

/*
* This class will talk to the UI (view) and the maze/questions (model) and act as a medium between them (controller)
*/

package state;

import maze.Maze;
import maze.Room;
import question.Question;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface GameState extends Serializable {
    /** Events that listeners can hook onto. */
    enum GameEvent { MOVE, ROOM_CHANGE, WIN, LOSE, SAVE, LOAD }

    /** What states a room can be in. */
    enum RoomState { LOCKED, UNLOCKED, UNKNOWN }

    /** Generates a new default game. */
    void initiateState();

    /**
     * Load a game state given some info on what needs to be loaded.
     * @param loadPath Required information to load a specific game state.
     * @return If the operation was successful.
     */
    boolean loadState(String loadPath);

    /**
     * Save a game state given some info on what needs to be saved.
     * @param savePath Required information to save a specific game state.
     * @return If the operation was successful.
     */
    boolean saveState(String savePath);

    /** Retrieves the maze of the current game state. */
    Maze getMaze();
    /** Retrieves all the questions for each room of the current game state. */
    Map<Room, Question> getQuestions();
    /** Register an attempt to answer a question. */
    void attemptQuestion(Room room, String answer);

    /**
     * Retrieves the question of a given room.
     * TODO: Rename to getQuestionRoom.
     * @param room The room to be queried.
     * @return The Question of the room or null if there is none.
     */
    Question getRoomQuestion(Room room);

    /** Retrieves the current room of the game state. */
    Room getCurrentRoom();

    /**
     * Move the current game state's room to the given new room.
     * @param newRoom The new room to move to.
     */
    void moveToRoom(Room newRoom);

    /**
     * Get the neighbors of the current game state's room.
     * @return The neighbors of the current room.
     */
    Set<Room> getCurrentNeighbors();

    /**
     * Get the amount of rooms away a given room is from the end.
     * @param room Room to query for distance.
     * @return How far away the given room is from the end. -1 is returned if there is no path.
     */
    int getDistanceToEnd(Room room);

    /**
     * Check the state the given room is in.
     * @param room The room meant to be checked.
     * @return The state of the given room.
     */
    RoomState getRoomState(Room room);

    /**
     * Set the state of a given room.
     * @param room The room that is meant to be changed.
     * @param state The new state of the given room.
     */
    void setRoomState(Room room, RoomState state);

    /**
     * Add a new object that will listen to changes.
     * @param theListener The object that will now listen for game state events.
     */
    void addPropertyChangeListener(final PropertyChangeListener theListener);

    /**
     * Remove an object that will listen to changes.
     * @param theListener The object that will no longer listen for game state events.
     */
    void removePropertyChangeListener(final PropertyChangeListener theListener);
}

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
    enum GameEvent { MOVE, ANSWER, UNLOCK }
    enum RoomState { LOCKED, UNLOCKED, UNKNOWN }

    void initiateState();
    void loadState(String loadInfo);
    void saveState(String saveInfo);

    Maze getMaze();
    Map<Room, Question> getQuestions();
    Question getRoomQuestion(Room room);

    Room getCurrentRoom();
    void moveToRoom(Room newRoom);
    Set<Room> getCurrentNeighbors();
    int getDistanceToEnd(Room room);

    RoomState checkRoomState(Room room);
    void setRoomState(Room room, RoomState state);

    void addPropertyChangeListener(final PropertyChangeListener theListener);
    void addPropertyChangeListener(final GameEvent event,
                                   final PropertyChangeListener theListener);

    void removePropertyChangeListener(final PropertyChangeListener theListener);
    void removePropertyChangeListener(final GameEvent event,
                                      final PropertyChangeListener theListener);
}

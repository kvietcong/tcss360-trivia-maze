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

    void initiateState();
    void loadState(String loadInfo);
    void saveState(String saveInfo);

    Maze getMaze();
    Map<Room, Question> getQuestions();
    Question getRoomQuestion(Room room);

    Room getCurrentRoom();
    Set<Room> getCurrentNeighbors();
    void moveToRoom(Room newRoom);
    int getDistanceToEnd(Room room);

    void unlockRoom(Room room);
    boolean isUnlocked(Room room);
    Set<Room> getUnlockedRooms();

    void addPropertyChangeListener(final PropertyChangeListener theListener);
    void addPropertyChangeListener(final GameEvent event,
                                   final PropertyChangeListener theListener);

    void removePropertyChangeListener(final PropertyChangeListener theListener);
    void removePropertyChangeListener(final GameEvent event,
                                      final PropertyChangeListener theListener);
}

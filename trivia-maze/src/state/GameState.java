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
    enum GameEvent {
        MOVE
    }

    GameState getInstance();

    void loadState(String loadInfo);
    void loadState(Maze maze, Map<Room, Question> questions);
    void saveState(String saveInfo);
    void initiateState();

    Maze getMaze();
    Room getCurrentRoom();
    void moveToRoom(Room newRoom);
    Question getRoomQuestion(Room room);
    Set<Room> getCurrentNeighbors();
    Map<Room, Question> getQuestions();

    void addPropertyChangeListener(final PropertyChangeListener theListener);
    void addPropertyChangeListener(final GameEvent event,
                                   final PropertyChangeListener theListener);

    void removePropertyChangeListener(final PropertyChangeListener theListener);
    void removePropertyChangeListener(final GameEvent event,
                                      final PropertyChangeListener theListener);
}

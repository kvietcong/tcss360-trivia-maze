package state;

import maze.Maze;
import maze.Room;
import question.Question;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface GameState extends Serializable {
    enum GameEvent { MOVE, ANSWER }

    void loadState(String loadInfo);
    void loadState(Maze maze, Map<Room, Question> questions, Room currentRoom);
    void saveState(String saveInfo);
    void initiateState();

    Maze getMaze();
    Room getCurrentRoom();
    void moveToRoom(Room newRoom);
    Question getRoomQuestion(Room room);
    Question getCurrentQuestion();
    Set<Room> getCurrentNeighbors();
    Map<Room, Question> getQuestions();

    void addPropertyChangeListener(final PropertyChangeListener theListener);
    void addPropertyChangeListener(final GameEvent event,
                                   final PropertyChangeListener theListener);

    void removePropertyChangeListener(final PropertyChangeListener theListener);
    void removePropertyChangeListener(final GameEvent event,
                                      final PropertyChangeListener theListener);
}

package state;

import maze.Maze;
import maze.Room;
import question.Question;

import java.io.Serializable;
import java.util.Map;

public interface GameState extends Serializable {
    GameState getInstance();

    void loadState(String info);
    void saveState(String info);
    void initiateState();

    Maze getMaze();
    Map<Room, Question> getQuestions();
}

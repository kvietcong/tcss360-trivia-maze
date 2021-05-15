package state;

import java.util.HashMap;
import java.util.Map;

import maze.Maze;
import maze.MazeGraph;
import maze.Room;
import question.Question;

public class GameStateSimple {
    private static final GameStateSimple INSTANCE = new GameStateSimple();

    private Maze maze;
    private Map<Room, Question> questions;

    private GameStateSimple() {
        initiateState();
    }

    public static GameState getInstance() { return (GameState) INSTANCE; }

    public void loadState(String info) { }

    public void saveState(String info) { }

    // Preload or Generate a new Maze
    public void initiateState() { }

    public Maze getMaze() { return INSTANCE.maze; }

    public Map<Room, Question> getQuestions() { return INSTANCE.questions; }
}

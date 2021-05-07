package maze;

public abstract class AbstractMaze implements Maze {
    public abstract Tile getTile(int x, int y);
    public abstract Tile getTile(String position);
}

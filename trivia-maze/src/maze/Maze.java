package maze;

public interface Maze {
    Tile getTile(int x, int y);
    Tile getTile(String position);
}

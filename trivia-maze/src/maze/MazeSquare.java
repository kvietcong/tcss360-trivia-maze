package maze;

public class MazeSquare extends AbstractMaze {
    private Tile[][] maze;
    private final int width;
    private final int length;

    public MazeSquare(int width, int length, String gameState) {
        this.width = width;
        this.length = length;
        maze = new TileSquare[width][length];
    }

    public MazeSquare(int length, int width) {
        this(width, length, "");
    }

    private void constructMaze(String gameState) {
        maze = maze;
    }

    @Override
    public Tile getTile(int x, int y) {
        if (x > width || x < 0 || y > length || y < 0) {
            throw new IllegalArgumentException("Please input proper coordinates");
        }
        return maze[x][y];
    }

    public Tile getTile(String position) { return getTile(position, ","); }

    public Tile getTile(String position, String delimiter) {
        String[] args = position.split(delimiter);
        if (args.length < 2) {
            throw new IllegalArgumentException("Please input proper coordinates");
        }
        return maze[Integer.parseInt(args[0])][Integer.parseInt(args[1])];
    }
}

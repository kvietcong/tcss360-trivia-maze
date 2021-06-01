package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class MazeReader {

    public static Map<Room, Set<Room>> readMaze(String fileName) {
        Map<Room, Set<Room>> rooms = new HashMap<>();
        Scanner input;
        try {
            input = new Scanner(new File("src/maze/" + fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
        int id = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Set<Room> roomConnections = new HashSet<>();
            Room room = new RoomSimple(id);
            while (!line.equals("")) {
                roomConnections.add(new RoomSimple(Integer.parseInt(line)));
                if (input.hasNextLine()) {
                    line = input.nextLine();
                } else {
                    break;
                }
            }
            id++;
            rooms.put(room, roomConnections);
        }
        return rooms;
    }

    // FOR TESTING - DELETE LATER
    public static void main(String[] args) {
        Map<Room, Set<Room>> maze = MazeReader.readMaze("maze-0.txt");
        assert maze != null;
        maze.keySet().forEach(room -> System.out.println(maze.get(room)));
    }
}

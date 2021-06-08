package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class MazeReader {

    public static Map<Room, Set<Room>> readMaze(String filePath) {
        Map<Room, Set<Room>> rooms = new HashMap<>();
        Scanner input;
        try {
            input = new Scanner(new File(filePath));
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
}

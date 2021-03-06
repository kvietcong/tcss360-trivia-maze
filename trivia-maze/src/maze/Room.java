package maze;

import java.io.Serializable;

public interface Room extends Serializable, Comparable<Room> {
    /**
     * A method to retrieve a unique identifier from a room.
     * @return A unique identifier that can be used to reconstruct a room
     */
    int getID();
}

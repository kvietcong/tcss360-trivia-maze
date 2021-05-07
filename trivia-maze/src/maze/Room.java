package maze;

public interface Room {
    /**
     * A method to retrieve a unique identifier from a room
     * @return A unique identifier that can be used to reconstruct a room
     */
    String getID();
}

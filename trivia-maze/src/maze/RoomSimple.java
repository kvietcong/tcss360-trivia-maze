package maze;

public class RoomSimple extends AbstractRoom {
    /** The unique identifier of this room. */
    private final int id;

    /**
     * Construct a new simple room.
     * @param id The unique identifier of this room.
     */
    public RoomSimple(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() { return id; }

    @Override
    public int getID() { return id; }

    @Override
    public String toString() { return "Room [id = " + id + "]"; }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            throw new IllegalArgumentException("You are comparing two different types");
        }
        return this.id == ((RoomSimple) other).id;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     */
    @Override
    public int compareTo(Room other) {
        if (other == null) {
            throw new IllegalArgumentException("You can't compare a null room!");
        }
        return Integer.compare(id, other.getID());
    }
}

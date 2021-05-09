package maze;

public class RoomSimple extends AbstractRoom {
    private final int id;

    public RoomSimple(int id) { this.id = id; }

    @Override
    public int hashCode() { return id; }

    @Override
    public String getID() { return toString(); }

    @Override
    public String toString() { return "Room [id = " + id + "]"; }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            throw new IllegalArgumentException("You are comparing two different types");
        }
        return this.id == ((RoomSimple) other).id;
    }
}

package gui;

import maze.Room;

import javax.swing.JButton;

/**
 * Simple JButton wrapper class to make JButtons associated with rooms sortable.
 */
public class JRoomButton extends JButton implements Comparable<JRoomButton> {
    /** Room that JButton represents. */
    private final Room room;

    /**
     * Creates a new button that hold information about a room.
     * @param label Button's label
     * @param room Room that button represents
     */
    public JRoomButton(String label, Room room) {
        super(label);
        this.room = room;
    }

    @Override
    public int compareTo(JRoomButton other) {
        return Integer.compare(room.getID(), other.room.getID());
    }

    /** @return The room of the button. */
    public Room getRoom() {
        return room;
    }
}

package gui;

import maze.Room;

import javax.swing.*;

/**
 * Simple JButton wrapper class to make JButtons associated with rooms sortable
 */
public class JRoomButton extends JButton implements Comparable<JRoomButton> {
    private final Room room;
    public JRoomButton(String label, Room room) {
        super(label);
        this.room = room;
    }

    @Override
    public int compareTo(JRoomButton other) {
        return Integer.compare(room.getID(), other.room.getID());
    }

    public Room getRoom() {
        return room;
    }
}

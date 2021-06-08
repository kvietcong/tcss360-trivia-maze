package test;

import maze.Room;
import maze.RoomSimple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room room0;
    private Room room1;
    private Room roomNegOne;

    @BeforeEach
    void setUp() {
        room0 = new RoomSimple(0);
        room1 = new RoomSimple(1);
        roomNegOne = new RoomSimple(-1);
    }

    @Test
    void getID() {
        assertAll("Test that IDs are accurate.",
                () -> assertEquals(0, room0.getID(), "ID is not accurate"),
                () -> assertEquals(1, room1.getID(), "ID is not accurate"),
                () -> assertEquals(-1, roomNegOne.getID(), "ID is not accurate")
        );
    }

    @Test
    void differentIDNotEqual() {
        assertAll("Test that IDs are unique for different rooms.",
                () -> assertNotEquals(room0.getID(), room1.getID(), "IDs are not unique"),
                () -> assertNotEquals(room1.getID(), roomNegOne.getID(), "IDs are not unique"),
                () -> assertNotEquals(roomNegOne.getID(), room0.getID(), "IDs are not unique")
        );
    }

    @Test
    void sameIDEqual() {
        assertAll("Test that different instances with same ID are equal.",
                () -> assertEquals(new RoomSimple(0), room0, "ID is not accurate"),
                () -> assertEquals(new RoomSimple(1), room1, "ID is not accurate"),
                () -> assertEquals(new RoomSimple(-1), roomNegOne, "ID is not accurate")
        );
    }

    @Test
    void testHashCode() {
        assertAll("Test that hash code is same as ID.",
                () -> assertEquals(room0.getID(), room0.hashCode(), "Hash code is not accurate"),
                () -> assertEquals(room1.getID(), room1.hashCode(), "Hash code is not accurate"),
                () -> assertEquals(roomNegOne.getID(), roomNegOne.hashCode(), "Hash code is not accurate")
        );
        assertAll("Test that hash codes are unique for different rooms.",
                () -> assertNotEquals(room0.hashCode(), room1.hashCode(), "Hash codes are not unique"),
                () -> assertNotEquals(room1.hashCode(), roomNegOne.hashCode(), "Hash codes are not unique"),
                () -> assertNotEquals(roomNegOne.hashCode(), room0.hashCode(), "Hash codes are not unique")
        );
    }
}
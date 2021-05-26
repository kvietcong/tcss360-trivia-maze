package gui;

import maze.Room;
import question.Question;
import state.GameState;
import state.GameStateSimple;
import state.GameState.RoomState;

import static state.GameState.GameEvent.*;
import static state.GameState.RoomState.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private static final GameState STATE = GameStateSimple.getInstance();
    private static final Set<String> SIGNALS = new HashSet<>(Arrays.asList(MOVE.toString(), ROOM_CHANGE.toString()));

    private final Set<JRoomButton> unlockedRooms;
    private final Set<JRoomButton> unknownRooms;
    private final Set<JLabel> lockedRooms;
    private final JLabel currentRoom;

    private GridBagConstraints gbc;
    private JPanel questionPanel = new JPanel();

    /**
     * Initialize the maze panel and menu bar.
     * */
    public GamePanel(){
        // TODO: Remove this hard coded load state when done testing
        STATE.loadState("./test.maze");
        // TODO: Remove this hard coded load state when done testing

        STATE.addPropertyChangeListener(this);

        lockedRooms = new HashSet<>();
        unknownRooms = new HashSet<>();
        unlockedRooms = new HashSet<>();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        resetConstraints();

        currentRoom = new JLabel();
        currentRoom.setFont(new Font("Serif", Font.BOLD, 24));
        add(currentRoom);

        resetConstraints();
        gbc.gridy++;
        add(new JLabel("Neighboring Unlocked Rooms:"), gbc);

        gbc.gridy++;
        add(new JLabel("Neighboring Unknown Rooms:"), gbc);

        gbc.gridy++;
        add(new JLabel("Neighboring Locked Rooms:"), gbc);

        refresh();
    }

    public void resetConstraints() {
        // Set Default Constraints
        // Grid Bag Reference: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 15;
    }

    public void refresh() {
        unlockedRooms.forEach(this::remove);
        unknownRooms.forEach(this::remove);
        lockedRooms.forEach(this::remove);
        unlockedRooms.clear();
        unknownRooms.clear();
        lockedRooms.clear();

        currentRoom.setText("Neighboring Rooms to " + STATE.getCurrentRoom().toString());

        Set<Room> neighbors = STATE.getCurrentNeighbors();
        neighbors.forEach(neighbor -> {
            RoomState state = STATE.getRoomState(neighbor);
            String label = neighbor.toString();
            if (state == LOCKED) {
                lockedRooms.add(new JLabel(label));
            } else {
                createRoomButton(neighbor, state, label);
            }
        });

        resetConstraints();
        gbc.gridx = 1;
        gbc.gridy++;
        unlockedRooms.stream().sorted().forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        gbc.gridx = 1;
        gbc.gridy++;
        unknownRooms.stream().sorted().forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        gbc.gridx = 1;
        gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        lockedRooms.stream().sorted().forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        revalidate();
        repaint();
    }

    public void createRoomButton(Room room, RoomState state, String label) {
        Question question = STATE.getRoomQuestion(room);
        JRoomButton newButton = new JRoomButton(
                "<html>" + label
                + "<br/>Topics: "
                + String.join(", ", question.getTopics())
                + "<br/>" + STATE.getDistanceToEnd(room) + " rooms from the end"
                + "</html>", room);

        switch (state) {
            case UNLOCKED -> {
                newButton.addActionListener(action -> {
                    STATE.moveToRoom(room);
                });
                unlockedRooms.add(newButton);
            }
            case UNKNOWN -> {
                newButton.addActionListener(action -> {
                    remove(questionPanel);
                    questionPanel = new QuestionChoicePanel(room, question, STATE);
                    add(questionPanel);
                    refresh();
                });
                unknownRooms.add(newButton);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param event A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (SIGNALS.contains(event.getPropertyName())) {
            remove(questionPanel);
            refresh();
        }
    }
}

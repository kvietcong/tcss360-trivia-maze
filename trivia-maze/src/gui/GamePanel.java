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

    private final Set<JButton> unlockedRooms;
    private final Set<JButton> unknownRooms;
    private final Set<JLabel> lockedRooms;

    private GridBagConstraints gbc;
    private JPanel questionPanel;

    /**
     * Initialize the maze panel and menu bar.
     * */
    public GamePanel(){
        // TODO: Remove this hard coded load state when done testing
        STATE.loadState("./test.maze");
        // TODO: Remove this hard coded load state when done testing

        STATE.addPropertyChangeListener(this);
        questionPanel = new QuestionChoicePanel();
        add(questionPanel);

        lockedRooms = new HashSet<>();
        unknownRooms = new HashSet<>();
        unlockedRooms = new HashSet<>();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        resetConstraints();

        gbc.gridwidth = 100;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Neighboring Rooms"), gbc);

        resetConstraints();
        gbc.gridy++;
        add(new JLabel("Unlocked Rooms:"), gbc);

        gbc.gridy++;
        add(new JLabel("Unknown Rooms:"), gbc);

        gbc.gridy++;
        add(new JLabel("Locked Rooms:"), gbc);

        refresh();
    }

    public void resetConstraints() {
        // Set Default Constraints
        // Grid Bag Reference: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
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
        unlockedRooms.forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        gbc.gridx = 1;
        gbc.gridy++;
        unknownRooms.forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        gbc.gridx = 1;
        gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        lockedRooms.forEach(element -> {
            add(element, gbc);
            gbc.gridx++;
        });

        revalidate();
        repaint();
    }

    public void createRoomButton(Room room, RoomState state, String label) {
        Question question = STATE.getRoomQuestion(room);
        JButton newButton = new JButton(
                "<html>" + label
                + "<br/>Topics: "
                + String.join(", ", question.getTopics())
                + "<br/>" + STATE.getDistanceToEnd(room) + " rooms from the end"
                + "</html>");

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

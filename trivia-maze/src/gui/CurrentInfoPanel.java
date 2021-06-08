package gui;

import constants.C;
import maze.Room;
import question.Question;
import state.GameState;
import state.GameStateSimple;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentInfoPanel extends JPanel implements PropertyChangeListener {
    /** State of the game. */
    private static final GameState STATE = GameStateSimple.getInstance();
    /** All the buttons for unlocked rooms. */
    private final Set<JRoomButton> unlockedButtons = new HashSet<>();
    /** All the buttons for unknown rooms. */
    private final Set<JRoomButton> unknownButtons = new HashSet<>();
    /** All the buttons for locked rooms. */
    private final Set<JRoomButton> lockedButtons = new HashSet<>();
    /** Panel to display neighboring room info. */
    private final JPanel mazeNeighborPanel = new JPanel();
    /** Title for the current room. */
    private final JLabel currentRoomTitle = new JLabel();
    /** Function to run when a question needs to be answered. */
    private final BiConsumer<Room, Question> triviaButtonFunction;
    /** The progress bar. */
    private final JProgressBar progressBar;

    /**
     * Constructs a new panel that displays the current info about the state.
     * @param triviaButtonFunction What to run to pop up a trivia panel.
     */
    public CurrentInfoPanel(BiConsumer<Room, Question> triviaButtonFunction) {
        JPanel centerTitle = new JPanel();
        centerTitle.setLayout(new GridBagLayout());
        currentRoomTitle.setFont(new Font("Arial", Font.BOLD, C.H1));
        centerTitle.add(currentRoomTitle);

        mazeNeighborPanel.setLayout(new GridLayout(0, 1));

        setLayout(new BorderLayout());
        add(centerTitle, BorderLayout.NORTH);
        add(mazeNeighborPanel, BorderLayout.CENTER);

        this.triviaButtonFunction = triviaButtonFunction;

        progressBar = new JProgressBar(
                JProgressBar.HORIZONTAL, 0, C.MAX_PROGRESS);
        progressBar.setStringPainted(true);

        JLabel progressTitle = new JLabel("Progress");
        progressTitle.setFont(new Font("Arial", Font.BOLD, C.H3));
        JPanel progressTitleCenter = new JPanel();
        progressTitleCenter.setLayout(new GridBagLayout());
        progressTitleCenter.add(progressTitle);

        JPanel progressContainer = new JPanel();
        progressContainer.setPreferredSize(new Dimension(-1, C.H1 * 2));
        progressContainer.setLayout(new GridLayout(0, 1));
        progressContainer.add(progressTitleCenter);
        progressContainer.add(progressBar);

        add(progressContainer, BorderLayout.SOUTH);
        setBorder(C.BORDER);

        STATE.addPropertyChangeListener(this);
    }

    private void refreshCurrentInfo() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        mazeNeighborPanel.removeAll();
        mazeNeighborPanel.setBorder(C.BORDER);

        currentRoomTitle.setText(C.wrapHTML(
                "You are in " + STATE.getCurrentRoom().toString()));

        progressBar.setValue(STATE.getProgress());
        progressBar.updateUI();

        STATE.getCurrentNeighbors().forEach(this::createRoomButton);

        StringBuilder label = new StringBuilder();

        Room currentRoom = STATE.getCurrentRoom();
        label.append(currentRoom)
                .append("<br/>")
                .append("You are here")
                .append("<br/>")
                .append(STATE.getDistanceToEnd(currentRoom))
                .append(" rooms from the end");
        JRoomButton currentRoomButton =
                new JRoomButton(C.wrapHTML(label.toString()), currentRoom);
        currentRoomButton.setEnabled(false);

        mazeNeighborPanel.add(containerizeButtons(
                "Neighboring Rooms", Stream.of(
                        unlockedButtons, unknownButtons, lockedButtons,
                        Collections.singleton(currentRoomButton))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        ));

        revalidate();
        repaint();
    }

    private JPanel containerizeButtons(String label, Set<JRoomButton> buttons) {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());
        container.add(new JLabel(C.wrapHTML(label)));
        buttons.stream().sorted().forEach(container::add);
        return container;
    }

    private void createRoomButton(Room room) {
        GameState.RoomState state = STATE.getRoomState(room);
        Question question = STATE.getRoomQuestion(room);
        StringBuilder label = new StringBuilder();

        // Construct a proper label to display in the button
        label.append(room);

        label.append("<br/>")
                .append("Topics: ")
                .append(String.join(", ", question.getTopics()));

        label.append("<br/>")
                .append(STATE.getDistanceToEnd(room))
                .append(" rooms from the end");


        JRoomButton newButton =
                new JRoomButton(C.wrapHTML(label.toString()), room);

        switch (state) {
            case UNLOCKED -> {
                newButton.addActionListener(action -> STATE.moveToRoom(room));
                newButton.setForeground(Color.decode("#2E3440"));
                newButton.setBackground(Color.decode("#A3BE8C"));
                unlockedButtons.add(newButton);
            }
            case UNKNOWN -> {
                newButton.addActionListener(action ->
                        triviaButtonFunction.accept(room, question));
                newButton.setForeground(Color.decode("#2E3440"));
                newButton.setBackground(Color.decode("#EBCB8B"));
                unknownButtons.add(newButton);
            }
            case LOCKED -> {
                newButton.setBackground(Color.decode("#BF616A"));
                newButton.addActionListener(action -> {
                    JOptionPane.showMessageDialog(this,
                            "This room is locked FOREVER",
                            "Locked Room!", JOptionPane.ERROR_MESSAGE);
                });
                lockedButtons.add(newButton);
            }
            default -> throw new
                    IllegalArgumentException("Not a valid room state!");
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
        GameState.GameEvent gameEvent =
                GameState.GameEvent.valueOf(event.getPropertyName());
        switch (gameEvent) {
            case MOVE, LOAD, ROOM_CHANGE -> refreshCurrentInfo();
            default -> { }
        }
        revalidate();
        repaint();
    }
}

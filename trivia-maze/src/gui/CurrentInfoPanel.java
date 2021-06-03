package gui;

import maze.Room;
import question.Question;
import state.GameState;
import state.GameStateSimple;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

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
        currentRoomTitle.setFont(new Font("Arial", Font.BOLD, UI.H1));
        centerTitle.add(currentRoomTitle);

        mazeNeighborPanel.setLayout(new GridLayout(0, 1));

        setLayout(new BorderLayout());
        add(centerTitle, BorderLayout.NORTH);
        add(mazeNeighborPanel, BorderLayout.CENTER);

        this.triviaButtonFunction = triviaButtonFunction;

        progressBar = new JProgressBar(
                JProgressBar.VERTICAL, 0, UI.MAX_PROGRESS);
        progressBar.setStringPainted(true);

        JPanel progressContainer = new JPanel();
        progressContainer.setLayout(
                new BoxLayout(progressContainer, BoxLayout.Y_AXIS));

        JPanel progressBarExpander = new JPanel();
        progressBarExpander.setLayout(new GridLayout(0, 1));
        progressBarExpander.add(progressBar);

        // Extra spaces for centering text :(
        JLabel progressTitle = new JLabel("Progress  ");
        progressTitle.setFont(new Font("Arial", Font.BOLD, UI.H4));

        progressContainer.add(progressTitle);
        progressContainer.add(progressBarExpander);
        add(progressContainer, BorderLayout.EAST);
        setBorder(UI.BORDER);

        STATE.addPropertyChangeListener(this);
    }

    private void refreshCurrentInfo() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        mazeNeighborPanel.removeAll();
        mazeNeighborPanel.setBorder(UI.BORDER);

        currentRoomTitle.setText(UI.wrapHTML(
                "You are in " + STATE.getCurrentRoom().toString()));

        int totalDistance = STATE.getDistanceToEnd(STATE.getStartRoom());
        int currentDistance = STATE.getDistanceToEnd(STATE.getCurrentRoom());
        progressBar.setValue((int)
                ((float) (totalDistance - currentDistance)
                        / totalDistance
                        * UI.MAX_PROGRESS));
        progressBar.updateUI();

        STATE.getCurrentNeighbors().forEach(this::createRoomButton);

        mazeNeighborPanel.add(containerizeButtons(
                "Neighboring Unlocked Rooms", unlockedButtons));
        mazeNeighborPanel.add(containerizeButtons(
                "Neighboring Unknown Rooms", unknownButtons));
        mazeNeighborPanel.add(containerizeButtons(
                "Neighboring Locked Rooms", lockedButtons));

        revalidate();
        repaint();
    }

    private JPanel containerizeButtons(String label, Set<JRoomButton> buttons) {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());
        container.add(new JLabel(UI.wrapHTML(label)));
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
                new JRoomButton(UI.wrapHTML(label.toString()), room);

        switch (state) {
            case UNLOCKED -> {
                newButton.addActionListener(action -> STATE.moveToRoom(room));
                unlockedButtons.add(newButton);
            }
            case UNKNOWN -> {
                newButton.addActionListener(action ->
                        triviaButtonFunction.accept(room, question));
                unknownButtons.add(newButton);
            }
            case LOCKED -> {
                newButton.setEnabled(false);
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

package gui;

import maze.Room;
import question.Question;
import state.GameState;
import state.GameStateSimple;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
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
    private final Set<JRoomButton> unlockedButtons = new HashSet<>();
    private final Set<JRoomButton> unknownButtons = new HashSet<>();
    private final Set<JRoomButton> lockedButtons = new HashSet<>();
    private final JPanel mazeNeighborPanel = new JPanel();
    private final JLabel mazeTitle = new JLabel();
    private final BiConsumer<Room, Question> triviaButtonFunction;
    private final JProgressBar progressBar;

    public CurrentInfoPanel(final BiConsumer<Room, Question> triviaButtonFunction) {
        JPanel centerTitle = new JPanel();
        centerTitle.setLayout(new GridBagLayout());
        mazeTitle.setFont(new Font("Arial", Font.BOLD, 48));
        centerTitle.add(mazeTitle);

        mazeNeighborPanel.setLayout(new GridLayout(0, 1));

        setLayout(new BorderLayout());
        add(centerTitle, BorderLayout.NORTH);
        add(mazeNeighborPanel, BorderLayout.CENTER);

        this.triviaButtonFunction = triviaButtonFunction;

        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        progressBar.setStringPainted(true);

        JPanel progressContainer = new JPanel();
        progressContainer.setLayout(new BoxLayout(progressContainer, BoxLayout.Y_AXIS));

        JPanel progressBarExpander = new JPanel();
        progressBarExpander.setLayout(new GridLayout(0, 1));
        progressBarExpander.add(progressBar);

        JLabel progressTitle = new JLabel("Progress  "); // Extra spaces for centering text :(
        progressTitle.setFont(new Font("Arial", Font.BOLD, 15));

        progressContainer.add(progressTitle);
        progressContainer.add(progressBarExpander);
        add(progressContainer, BorderLayout.EAST);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        STATE.addPropertyChangeListener(this);
    }

    private void refreshCurrentInfo() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        mazeNeighborPanel.removeAll();
        mazeNeighborPanel.setBorder(new EmptyBorder(10, 10, 10 , 10));

        mazeTitle.setText("<html>You are in " + STATE.getCurrentRoom().toString() + "</html>");

        int totalDistance = STATE.getDistanceToEnd(STATE.getStartRoom());
        int currentDistance = STATE.getDistanceToEnd(STATE.getCurrentRoom());
        progressBar.setValue((int) ((float) (totalDistance - currentDistance) / totalDistance * 100));
        progressBar.updateUI();

        STATE.getCurrentNeighbors().forEach(this::createRoomButton);

        mazeNeighborPanel.add(containerizeButtons("Neighboring Unlocked Rooms", unlockedButtons));
        mazeNeighborPanel.add(containerizeButtons("Neighboring Unknown Rooms", unknownButtons));
        mazeNeighborPanel.add(containerizeButtons("Neighboring Locked Rooms", lockedButtons));

        revalidate();
        repaint();
    }

    private JPanel containerizeButtons(String label, Set<JRoomButton> buttons) {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());
        container.add(new JLabel(label));
        buttons.stream().sorted().forEach(container::add);
        return container;
    }

    private void createRoomButton(Room room) {
        GameState.RoomState state = STATE.getRoomState(room);
        Question question = STATE.getRoomQuestion(room);
        StringBuilder label = new StringBuilder(room.toString());

        // Construct a proper label to display in the button
        label.insert(0, "<html>");

        label.append("<br/>");
        label.append("Topics: ").append(String.join(", ", question.getTopics()));

        label.append("<br/>");
        label.append(STATE.getDistanceToEnd(room)).append(" rooms from the end");

        label.append("</html>");


        JRoomButton newButton = new JRoomButton(label.toString(), room);

        switch (state) {
            case UNLOCKED -> {
                newButton.addActionListener(action -> STATE.moveToRoom(room));
                unlockedButtons.add(newButton);
            }
            case UNKNOWN -> {
                newButton.addActionListener(action -> triviaButtonFunction.accept(room, question));
                unknownButtons.add(newButton);
            }
            case LOCKED -> {
                newButton.setEnabled(false);
                lockedButtons.add(newButton);
            }
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
        GameState.GameEvent gameEvent = GameState.GameEvent.valueOf(event.getPropertyName());
        switch (gameEvent) {
            case MOVE, LOAD, ROOM_CHANGE -> refreshCurrentInfo();
            default -> {}
        }
        revalidate();
        repaint();
    }
}

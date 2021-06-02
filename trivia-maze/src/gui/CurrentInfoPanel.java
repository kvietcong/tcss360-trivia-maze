package gui;

import maze.Room;
import question.Question;
import state.GameState;
import state.GameStateSimple;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class CurrentInfoPanel extends JPanel implements PropertyChangeListener {
    private static final GameState STATE = GameStateSimple.getInstance();
    private final Set<JRoomButton> unlockedButtons = new HashSet<>();
    private final Set<JRoomButton> unknownButtons = new HashSet<>();
    private final Set<JRoomButton> lockedButtons = new HashSet<>();
    private final JPanel mazeNeighborPanel = new JPanel();
    private final JLabel mazeTitle = new JLabel();
    private final BiConsumer<Room, Question> triviaButtonFunction;
    private final JProgressBar progressBar;

    public CurrentInfoPanel(BiConsumer<Room, Question> triviaButtonFunction) {
        JPanel centerTitle = new JPanel();
        centerTitle.setLayout(new GridBagLayout());
        mazeTitle.setFont(new Font("Arial", Font.BOLD, 48));

        mazeNeighborPanel.setLayout(new GridLayout(0, 1));

        centerTitle.add(mazeTitle);
        setLayout(new BorderLayout());
        add(centerTitle, BorderLayout.NORTH);
        add(mazeNeighborPanel, BorderLayout.CENTER);

        this.triviaButtonFunction = triviaButtonFunction;

        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        progressBar.setStringPainted(true);

        JPanel progress = new JPanel();
        progress.setLayout(new GridLayout(1, 2));
        progress.add(progressBar);
        progress.setPreferredSize(new Dimension(50, 25));
        add(progress, BorderLayout.EAST);

        STATE.addPropertyChangeListener(this);
    }

    private void refreshCurrentInfo() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        mazeNeighborPanel.removeAll();
        mazeNeighborPanel.setBorder(new EmptyBorder(10, 10, 10 , 10));

        mazeTitle.setText("You are in " + STATE.getCurrentRoom().toString());

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

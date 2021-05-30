package gui;

import maze.Room;
import question.Question;
import state.GameState;
import state.GameState.RoomState;
import state.GameStateSimple;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static state.GameState.GameEvent;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private static final GameState STATE = GameStateSimple.getInstance();

    private final Set<JRoomButton> unlockedButtons = new HashSet<>();
    private final Set<JRoomButton> unknownButtons = new HashSet<>();
    private final Set<JRoomButton> lockedButtons = new HashSet<>();
    private final JLabel mazeTitle = new JLabel();
    private final Runnable showMainMenu;

    private final JPanel mazePanel = new JPanel();
    private final JPanel mazeNeighborPanel = new JPanel();

    private final JPanel triviaPanel = new JPanel();

    /**
     * Initialize a new panel to handle game GUI
     * @param showMainMenu Go back to main menu
     */
    public GamePanel(Runnable showMainMenu) {
        // TODO: Remove this hard coded load state when done testing
        STATE.loadState("./test.maze");
        // TODO: Remove this hard coded load state when done testing

        this.showMainMenu = showMainMenu;
        STATE.addPropertyChangeListener(this);

        JPanel centerTitle = new JPanel();
        centerTitle.setLayout(new GridBagLayout());
        mazeTitle.setFont(new Font("Arial", Font.BOLD, 48));
        centerTitle.add(mazeTitle);

        mazeNeighborPanel.setLayout(new GridLayout(0, 1));

        mazePanel.setLayout(new BorderLayout());
        mazePanel.add(centerTitle, BorderLayout.NORTH);
        mazePanel.add(mazeNeighborPanel, BorderLayout.CENTER);

        setLayout(new CardLayout());
        add(mazePanel, "CURRENT_INFO");
        add(triviaPanel, "ANSWER_QUESTION");

        refresh();
    }

    public void refresh() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        mazeNeighborPanel.removeAll();
        mazeNeighborPanel.add(Box.createVerticalStrut(50));

        mazeTitle.setText("You are in " + STATE.getCurrentRoom().toString());

        STATE.getCurrentNeighbors().forEach(this::createRoomButton);

        mazeNeighborPanel.add(containerizeButtons("Neighboring Unlocked Rooms", unlockedButtons));
        mazeNeighborPanel.add(containerizeButtons("Neighboring Unknown Rooms", unknownButtons));
        mazeNeighborPanel.add(containerizeButtons("Neighboring Locked Rooms", lockedButtons));

        mazeNeighborPanel.add(Box.createVerticalStrut(50));
        show("CURRENT_INFO");
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

    public void createRoomButton(Room room) {
        RoomState state = STATE.getRoomState(room);
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
                newButton.addActionListener(action -> {
                    triviaPanel.removeAll();
                    triviaPanel.add(new TriviaPanel(room, question, STATE, this::show));
                    show("ANSWER_QUESTION");
                    revalidate();
                    repaint();
                });
                unknownButtons.add(newButton);
            }
            case LOCKED -> {
                newButton.setEnabled(false);
                lockedButtons.add(newButton);
            }
        }
    }

    public void show(String card) {
        ((CardLayout) this.getLayout()).show(this, card);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param event A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        GameEvent gameEvent = GameEvent.valueOf(event.getPropertyName());
        switch (gameEvent) {
            case MOVE, ROOM_CHANGE -> refresh();
            case WIN, LOSE -> {
                removeAll();
                add(new EndPanel(gameEvent, showMainMenu), "END");
                show("END");
                revalidate();
                refresh();
            }
            default -> throw new IllegalStateException("Unexpected value: " + event.getPropertyName());
        }
    }
}

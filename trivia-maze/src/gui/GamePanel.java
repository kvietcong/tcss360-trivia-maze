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

import static state.GameState.GameEvent;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private static final GameState STATE = GameStateSimple.getInstance();

    private final Set<JRoomButton> unlockedButtons = new HashSet<>();
    private final Set<JRoomButton> unknownButtons = new HashSet<>();
    private final Set<JRoomButton> lockedButtons = new HashSet<>();
    private final JLabel currentRoom = new JLabel();
    private final CardLayout cardLayout = new CardLayout();

    // Main containers
    private JPanel currentInfo = new JPanel();
    private JPanel answerQuestion = new JPanel();

    // Sub containers
    private JPanel neighboringInfo = new JPanel();
    private JPanel questionPanel = new JPanel();

    /**
     * Initialize the maze panel and menu bar.
     * */
    public GamePanel(){
        // TODO: Remove this hard coded load state when done testing
        STATE.loadState("./test.maze");
        // TODO: Remove this hard coded load state when done testing

        STATE.addPropertyChangeListener(this);

        JPanel centerTitle = new JPanel();
        centerTitle.setLayout(new GridBagLayout());
        currentRoom.setFont(new Font("Serif", Font.BOLD, 48));
        centerTitle.add(currentRoom);

        neighboringInfo.setLayout(new GridLayout(0, 1));

        currentInfo.setLayout(new BorderLayout());
        currentInfo.add(centerTitle, BorderLayout.NORTH);
        currentInfo.add(neighboringInfo, BorderLayout.CENTER);

        setLayout(cardLayout);
        add(currentInfo, "CURRENT_INFO");
        add(answerQuestion, "ANSWER_QUESTION");

        refresh();
    }

    public void refresh() {
        unlockedButtons.clear();
        unknownButtons.clear();
        lockedButtons.clear();
        neighboringInfo.removeAll();
        cardLayout.show(this, "CURRENT_INFO");

        currentRoom.setText("You are in " + STATE.getCurrentRoom().toString());

        STATE.getCurrentNeighbors().forEach(this::createRoomButton);

        neighboringInfo.add(containerizeButtons("Neighboring Unlocked Rooms", unlockedButtons));
        neighboringInfo.add(containerizeButtons("Neighboring Unknown Rooms", unknownButtons));
        neighboringInfo.add(containerizeButtons("Neighboring Locked Rooms", lockedButtons));

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
                    answerQuestion.remove(questionPanel);
                    questionPanel = new QuestionChoicePanel(room, question, STATE, this::show);
                    answerQuestion.add(questionPanel);
                    cardLayout.show(this, "ANSWER_QUESTION");
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
        cardLayout.show(this, card);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param event A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (GameEvent.valueOf(event.getPropertyName())) {
            case MOVE, ROOM_CHANGE -> refresh();
            default -> throw new IllegalStateException("Unexpected value: " + event.getPropertyName());
        }
    }
}

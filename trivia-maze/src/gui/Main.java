package gui;
import maze.Maze;
import maze.MazeGraph;
import maze.Room;
import maze.RoomSimple;
import question.Question;
import question.QuestionTF;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // All current code is for testing purposes
	    System.out.println("Hello World!");

        Question testQ = new QuestionTF("You are ok.", "false");
        JLabel question = new JLabel(testQ.getQuestion());
        JLabel prompt = new JLabel(testQ.getChoices()[0]);
        JButton trueButton = new JButton("True");
        JButton falseButton = new JButton("False");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(question);
        panel.add(prompt);
        panel.add(trueButton);
        panel.add(falseButton);

        JLabel correctLabel = new JLabel();
        panel.add(correctLabel);

        trueButton.addActionListener(event -> {
            boolean isSolved = testQ.isCorrectAnswer(trueButton.getText());
            correctLabel.setText(
                     isSolved ? "You are right" : "You are wrong");
            trueButton.setEnabled(!isSolved);
            falseButton.setEnabled(!isSolved);
        });
        falseButton.addActionListener(event -> {
            boolean isSolved = testQ.isCorrectAnswer(falseButton.getText());
            correctLabel.setText(
                    isSolved ? "You are right" : "You are wrong");
            trueButton.setEnabled(!isSolved);
            falseButton.setEnabled(!isSolved);
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(event -> {
            if (!trueButton.isEnabled()) {
                trueButton.setEnabled(true);
                falseButton.setEnabled(true);
                correctLabel.setText("");
            }
        });
        panel.add(resetButton);

        JFrame frame = new JFrame("Basic UI");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,720);
        frame.add(panel);
        frame.setVisible(true);

        Room room1 = new RoomSimple(1);
        Room room2 = new RoomSimple(2);
        Room room3 = new RoomSimple(3);

        Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room2);
        rooms1.add(room3);
        Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room1);
        Set<Room> rooms3 = new HashSet<>();
        rooms3.add(room1);

        Map<Room, Set<Room>> rooms = new HashMap<>();
        rooms.put(room1, rooms1);
        rooms.put(room2, rooms2);
        rooms.put(room3, rooms3);

        Maze maze = new MazeGraph(rooms);
        maze.forEach(room -> {
            var neighbors = maze.getNeighbors(room);
            StringBuilder res = new StringBuilder(" {{ Neighbors:");
            for (var neighbor : neighbors) {
                res.append(" ").append(neighbor);
            }
            res.append(" }}");
            System.out.println(room + res.toString());
        });

        try {
            System.out.println(serializableToString(maze));
        } catch (IOException ignored) { }
    }
    private static String serializableToString(Serializable o) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}

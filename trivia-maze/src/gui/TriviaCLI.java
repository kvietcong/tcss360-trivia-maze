/*
* This is a simple Command-Line Interface to use for the development of the Trivia Maze Game.
* This will be replaced by a graphics UI.
*/

package gui;

import java.util.Scanner;

public class TriviaCLI {

    Scanner INPUT;

    public TriviaCLI() {
        this.INPUT = new Scanner(System.in);
    }

    /**
     * Displays the given trivia question and choices and gets the user's input.
     * @param question Trivia question
     * @param choices Possible answers
     */
    public void runTriviaQuestion(String question, String[] choices) {
        System.out.println(question);
        for (String c : choices) {
            System.out.println(c);
        }
        System.out.println();
        System.out.print("Select your answer: ");

        String choice = INPUT.next();
    }

    public static void main(String[] args) {
        TriviaCLI cli = new TriviaCLI();

        cli.runTriviaQuestion("Are programmers happy", new String[]{"T", "F"});
    }
}

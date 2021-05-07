package maze;

import question.Question;

public abstract class AbstractTile implements Tile {
    private final int directionAmount;
    private final Question[] questions;

    protected AbstractTile(int directionAmount, Question[] questions) {
        this.directionAmount = directionAmount;
        this.questions = questions;
    }

    @Override
    public int getDirectionAmount() {
        return directionAmount;
    }

    @Override
    public Question getQuestion(int choice) {
        if (choice > directionAmount || choice < 0) {
            throw new IllegalArgumentException("Please input a proper choice");
        }
        return questions[choice];
    }
}

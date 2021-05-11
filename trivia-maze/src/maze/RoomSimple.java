package maze;

import question.Question;
import question.QuestionFactory;

public class RoomSimple extends AbstractRoom {
    private final int id;
    private final Question question;

    public RoomSimple(int id, Question question) {
        this.id = id;
        this.question = question;
    }

    @Override
    public int hashCode() { return id; }

    @Override
    public String getID() { return toString(); }

    @Override
    public Question getQuestion() { return this.question; }

    @Override
    public String toString() { return "Room [id = " + id + "]"; }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            throw new IllegalArgumentException("You are comparing two different types");
        }
        return this.id == ((RoomSimple) other).id;
    }
}

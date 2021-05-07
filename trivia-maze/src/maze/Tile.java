package maze;

import question.Question;

public interface Tile {
    int getDirectionAmount();
    Question getQuestion(int choice);
}

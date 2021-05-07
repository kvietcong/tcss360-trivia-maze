package main;

public abstract class AbstractQuestion implements Question {
    private final QuestionType type;
    private final String question;
    private boolean isSolved;

    protected AbstractQuestion(QuestionType type, String question, boolean isSolved) {
        this.type = type;
        this.isSolved = isSolved;
        this.question = question;
    }

    public QuestionType getType() { return type; }
    public String getQuestion() { return question; }
    public boolean getIsSolved() { return isSolved; }

    public boolean solve(String answer) {
        isSolved = isCorrectAnswer(answer);
        return isSolved;
    }
}

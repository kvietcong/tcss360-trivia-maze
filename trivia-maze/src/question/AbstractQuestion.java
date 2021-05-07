package question;

public abstract class AbstractQuestion implements Question {
    private final QuestionType type;
    private final String question;
    private final String prompt;
    private final String answer;
    private boolean isSolved;

    protected AbstractQuestion(QuestionType type,
                               boolean isSolved,
                               String question, String prompt, String answer) {
        this.type = type;
        this.isSolved = isSolved;
        this.question = question;
        this.prompt = prompt;
        this.answer = answer;
    }

    public QuestionType getType() { return type; }
    public String getQuestion() { return question; }
    public String getPrompt() { return prompt; }
    public boolean getIsSolved() { return isSolved; }

    public boolean isCorrectAnswer(String answer) {
        return answer.equalsIgnoreCase(this.answer);
    }

    public boolean solve(String answer) {
        isSolved = isCorrectAnswer(answer);
        return isSolved;
    }
}

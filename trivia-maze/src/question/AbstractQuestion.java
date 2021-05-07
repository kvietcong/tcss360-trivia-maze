package question;

public abstract class AbstractQuestion implements Question {
    private final QuestionType type;
    private final String question;
    private final String[] choices;
    private final String answer;

    protected AbstractQuestion(QuestionType type, String question, String[] choices, String answer) {
        this.type = type;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }

    public QuestionType getType() { return type; }
    public String getQuestion() { return question; }
    public String[] getChoices() { return choices; }
    public boolean isCorrectAnswer(String answer) {
        return answer.equalsIgnoreCase(this.answer);
    }
}

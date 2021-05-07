package question;

public abstract class AbstractQuestion implements Question {
    private final QuestionType type;
    private final String question;
    private final String prompt;
    private final String answer;

    protected AbstractQuestion(QuestionType type, String question, String prompt, String answer) {
        this.type = type;
        this.question = question;
        this.prompt = prompt;
        this.answer = answer;
    }

    public QuestionType getType() { return type; }
    public String getQuestion() { return question; }
    public String getPrompt() { return prompt; }
    public boolean isCorrectAnswer(String answer) {
        return answer.equalsIgnoreCase(this.answer);
    }
}

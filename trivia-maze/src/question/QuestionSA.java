package question;

public final class QuestionSA extends AbstractQuestion {
    public QuestionSA(String question, String prompt, String answer) {
        super(QuestionType.SA, question, prompt, answer);
    }

    public QuestionSA(String question, String answer) {
        super(QuestionType.SA, question, "", answer);
    }
}

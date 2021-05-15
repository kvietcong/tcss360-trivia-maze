package question;

public final class QuestionTF extends AbstractQuestion {
    public QuestionTF(String question, String[] prompt, String answer) {
        super(QuestionType.TF, question, prompt, answer);
    }

    public QuestionTF(String question, String answer) {
        super(QuestionType.TF, question, new String[]{"True", "False"}, answer);
    }
}

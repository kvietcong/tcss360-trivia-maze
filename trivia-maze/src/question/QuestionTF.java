package question;

public final class QuestionTF extends AbstractQuestion {
    /**
     * Create a True/False question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param answer The answer.
     */
    public QuestionTF(String question, String[] choices, String answer) {
        super(QuestionType.TF, question, choices, answer);
    }

    /**
     * Create a True/False question
     * @param question The question to be asked.
     * @param answer The answer.
     */
    public QuestionTF(String question, String answer) {
        super(QuestionType.TF, question, new String[]{"True", "False"}, answer);
    }
}

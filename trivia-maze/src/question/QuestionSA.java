package question;

public final class QuestionSA extends AbstractQuestion {
    /**
     * Create a Short Answer question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param answer The answer.
     */
    public QuestionSA(String question, String[] choices, String answer) {
        super(QuestionType.SA, question, choices, answer);
    }

    /**
     * Create a Short Answer question
     * @param question The question to be asked.
     * @param answer The answer.
     */
    public QuestionSA(String question, String answer) {
        super(QuestionType.SA, question, new String[0], answer);
    }
}

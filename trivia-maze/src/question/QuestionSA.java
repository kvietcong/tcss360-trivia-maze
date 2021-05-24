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
}

package question;

public final class QuestionSA extends AbstractQuestion {
    /**
     * Create a Short Answer question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param topics The topics the Question falls under.
     * @param answer The answer.
     */
    public QuestionSA(String question, String[] topics, String[] choices, String answer) {
        super(QuestionType.SA, topics, question, choices, answer);
    }
}

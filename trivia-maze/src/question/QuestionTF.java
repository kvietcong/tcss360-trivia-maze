package question;

public final class QuestionTF extends AbstractQuestion {
    /**
     * Create a True/False question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param topics The topics the Question falls under.
     * @param answer The answer.
     */
    public QuestionTF(String question, String[] topics, String[] choices, String answer) {
        super(QuestionType.TF, topics, question, choices, answer);
    }
}

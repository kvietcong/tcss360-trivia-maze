package question;

public final class QuestionMC extends AbstractQuestion {
    /**
     * Create a Multiple Choice question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param topics The topics the Question falls under.
     * @param answer The answer.
     */
    public QuestionMC(String question, String[] topics, String[] choices, String answer) {
        super(QuestionType.MC, topics, question, choices, answer);
    }
}
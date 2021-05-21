package question;

public final class QuestionMC extends AbstractQuestion {
    /**
     * Create a Multiple Choice question
     * @param question The question to be asked.
     * @param choices The available choices.
     * @param answer The answer.
     */
    public QuestionMC(String question, String[] choices, String answer) {
        super(QuestionType.MC, question, choices, answer);
    }
}
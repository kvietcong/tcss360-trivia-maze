package question;

import java.io.Serializable;

public interface Question extends Serializable {
    /** The different types of questions available to the program. */
    enum QuestionType { MC, TF, SA }

    /**
     * Retrieve the current question's type.
     * @return The current question's type.
     */
    QuestionType getType();

    /**
     * Retrieve the question being asked.
     * @return The question being asked.
     */
    String getQuestion();

    /**
     * Retrieve the choices being offered for the question.
     * @return The choices being offered for the question.
     */
    String[] getChoices();

    /**
     * Retrieve the topics the question falls under.
     * @return The topics the question falls under.
     */
    String[] getTopics();

    /**
     * Check if a given answer is correct (Case insensitive).
     * @param answer The answer to check
     * @return If the given answer is correct
     */
    boolean isCorrectAnswer(String answer);
}
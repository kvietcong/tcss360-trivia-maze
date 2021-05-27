package question;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractQuestion implements Question {
    /** The type of the question. */
    private final QuestionType type;

    /** The question. */
    private final String question;

    /** The choices available for the question. */
    private final String[] choices;

    /** The answer for the question. */
    private final String answer;

    /** The topics for this question. */
    private final String[] topics;

    /**
     * Setup a general question with different pieces.
     * @param type The type of the question.
     * @param question The question.
     * @param choices The choices available to the question.
     * @param answer The answer of the question.
     */
    protected AbstractQuestion(QuestionType type, String[] topics,
                               String question, String[] choices, String answer) {
        if (
                type == null
                || topics == null
                || question == null
                || choices == null
                || answer == null
        ) {
            throw new IllegalArgumentException("You cannot have null Question parameters!");
        }

        this.type = type;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.topics = topics;
    }

    public QuestionType getType() { return type; }
    public String[] getTopics() { return topics; }
    public String getQuestion() { return question; }
    public String[] getChoices() { return choices; }
    public boolean isCorrectAnswer(String answer) {
        return answer.equalsIgnoreCase(this.answer);
    }

    @Override
    public String toString() {
        return question + "\nChoices: " + String.join(", ", choices) + "\nAnswer: " + answer;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        AbstractQuestion otherQuestion = (AbstractQuestion) other;
        return type == otherQuestion.type && question.equals(otherQuestion.question)
                && Arrays.equals(choices, otherQuestion.choices)
                && answer.equals(otherQuestion.answer)
                && Arrays.equals(topics, otherQuestion.topics);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, question, answer);
        result = 31 * result + Arrays.hashCode(choices);
        result = 31 * result + Arrays.hashCode(topics);
        return result;
    }
}

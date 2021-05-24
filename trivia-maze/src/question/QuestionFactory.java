package question;

public final class QuestionFactory {
    public static Question createQuestion(String question, String[] topics, String[] choices, String answer, String type) {
        return switch (type) {
            case "MC" -> new QuestionMC(question, topics, choices, answer);
            case "TF" -> new QuestionTF(question, topics, answer);
            case "SA" -> new QuestionSA(question, topics, answer);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}

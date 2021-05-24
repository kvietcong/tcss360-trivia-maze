package question;

public final class QuestionFactory {
    public static Question createQuestion(String question, String[] choices, String answer, String type) {
        return switch (type) {
            case "MC" -> new QuestionMC(question, choices, answer);
            case "TF" -> new QuestionTF(question, choices, answer);
            case "SA" -> new QuestionSA(question, choices, answer);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}

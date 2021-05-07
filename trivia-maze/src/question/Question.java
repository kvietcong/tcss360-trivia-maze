package question;

public interface Question {
    boolean isCorrectAnswer(String answer);
    enum QuestionType { MC, TF, SA }
    QuestionType getType();
    String getQuestion();
    String getPrompt();
}


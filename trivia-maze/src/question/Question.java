package question;

public interface Question {
    enum QuestionType { MC, TF, SA }
    QuestionType getType();

    String getQuestion();
    String getPrompt();

    boolean getIsSolved();
    boolean solve(String answer);
    boolean isCorrectAnswer(String answer);
}


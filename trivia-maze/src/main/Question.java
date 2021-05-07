package main;

public interface Question {
    enum QuestionType { MC, TF, SA }
    QuestionType getType();

    String getPrompt();
    String getQuestion();

    boolean getIsSolved();
    boolean solve(String answer);
    boolean isCorrectAnswer(String answer);
}


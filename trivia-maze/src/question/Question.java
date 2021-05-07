package question;

public interface Question {
    enum QuestionType { MC, TF, SA }
    QuestionType getType();
    String getQuestion();
    String[] getChoices();
    boolean isCorrectAnswer(String answer);
}
package question;

public class QuestionSA extends AbstractQuestion {
    public QuestionSA(boolean isSolved,
                      String question, String prompt, String answer) {
        super(QuestionType.SA, isSolved, question, prompt, answer);
    }

    public QuestionSA(boolean isSolved,
                      String question, String answer) {
        super(QuestionType.SA, isSolved, question, "", answer);
    }
}

package question;

public class QuestionTF extends AbstractQuestion {
    public QuestionTF(boolean isSolved,
                      String question, String prompt, String answer) {
        super(QuestionType.TF, isSolved, question, prompt, answer);
    }

    public QuestionTF(boolean isSolved,
                      String question, String answer) {
        super(QuestionType.TF, isSolved, question, "True or False?", answer);
    }
}

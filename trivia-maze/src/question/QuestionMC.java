package question;

public class QuestionMC extends AbstractQuestion {
    public QuestionMC(boolean isSolved,
                      String question, String prompt, String answer) {
        super(QuestionType.MC, isSolved, question, prompt, answer);
    }
}
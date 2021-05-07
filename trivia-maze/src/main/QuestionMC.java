package main;

import java.util.Locale;
import static main.Question.QuestionType.*;

public class QuestionMC extends AbstractQuestion {
    private final String answer;
    private final String prompt;

    public QuestionMC(String question, boolean isSolved, String answer, String prompt) {
        super(MC, question, isSolved);
        this.answer = answer;
        this.prompt = prompt;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public boolean isCorrectAnswer(String answer) {
        return answer.toLowerCase(Locale.ROOT).equals(this.answer);
    }
}

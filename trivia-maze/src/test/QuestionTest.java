package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.Question;
import question.QuestionFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question basicQuestionTF;
    private Question basicQuestionMC;
    private Question basicQuestionSA;

    @BeforeEach
    void setUp() {
        basicQuestionTF = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "TF");
        basicQuestionMC = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False", "Other"}, "True", "MC");
        basicQuestionSA = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{}, "True", "SA");
    }

    @Test
    void getType() {
        assertAll("Test that question type is correct.",
                () -> assertEquals(basicQuestionTF.getType(), Question.QuestionType.TF, "Wrong question type, should be TF"),
                () -> assertEquals(basicQuestionMC.getType(), Question.QuestionType.MC, "Wrong question type, should be MC"),
                () -> assertEquals(basicQuestionSA.getType(), Question.QuestionType.SA, "Wrong question type, should be SA")
        );
    }

    @Test
    void getQuestion() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "TF");
        assertEquals("Test?", question.getQuestion());
    }

    @Test
    void getQuestionEmptyString() {
        Question question = QuestionFactory.createQuestion("", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "TF");
        assertEquals("", question.getQuestion());
    }

    @Test
    void getChoicesTF() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "TF");
        assertEquals(Arrays.toString(question.getChoices()), Arrays.toString(new String[]{"True", "False"}));
    }

    @Test
    void getChoicesTFEmpty() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{}, "True", "TF");
        assertEquals(Arrays.toString(question.getChoices()), Arrays.toString(new String[]{"True", "False"}));
    }

    @Test
    void getChoicesSA() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "SA");
        assertEquals(0, question.getChoices().length);
    }

    @Test
    void getChoicesSAEmpty() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{}, "True", "SA");
        assertEquals(0, question.getChoices().length);
    }

    @Test
    void getChoicesMC() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"A", "B", "C", "D"}, "True", "MC");
        assertEquals(Arrays.toString(question.getChoices()), Arrays.toString(new String[]{"A", "B", "C", "D"}));
    }

    @Test
    void getChoicesMCEmpty() {
        Question question = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{}, "True", "MC");
        assertEquals(0, question.getChoices().length);
    }

    @Test
    void isCorrectAnswer() {
        assertAll("Test accuracy of checking correct answer.",
                () -> assertTrue(basicQuestionTF.isCorrectAnswer("True"), "Answer check should be true"),
                () -> assertTrue(basicQuestionMC.isCorrectAnswer("True"), "Answer check should be true"),
                () -> assertTrue(basicQuestionMC.isCorrectAnswer("True"), "Answer check should be true")
        );
    }

    @Test
    void isCorrectAnswerIgnoresCase() {
        assertAll("Test accuracy of checking correct answer while ignoring case.",
                () -> assertTrue(basicQuestionTF.isCorrectAnswer("tRuE"), "Answer check should be true"),
                () -> assertTrue(basicQuestionMC.isCorrectAnswer("true"), "Answer check should be true"),
                () -> assertTrue(basicQuestionMC.isCorrectAnswer("TRUE"), "Answer check should be true")
        );
    }

    @Test
    void isCorrectAnswerWrongGuesses() {
        assertAll("Test accuracy of checking correctness of wrong guesses.",
                () -> assertFalse(basicQuestionTF.isCorrectAnswer("false"), "Answer check should be false"),
                () -> assertFalse(basicQuestionMC.isCorrectAnswer("blah"), "Answer check should be false"),
                () -> assertFalse(basicQuestionMC.isCorrectAnswer("Can you repeat the question?"), "Answer check should be false")
        );
    }

    @Test
    void getTopics() {
        Question questionTF = QuestionFactory.createQuestion("Test?", new String[]{"Topic1", "Topic2", "Topic3"}, new String[]{"True", "False"}, "True", "TF");
        Question questionMC = QuestionFactory.createQuestion("Test?", new String[]{"Topic"}, new String[]{"True", "False"}, "True", "MC");
        Question questionSA = QuestionFactory.createQuestion("Test?", new String[]{}, new String[]{}, "True", "SA");

        assertAll("Test that topics are correct.",
                () -> assertEquals(Arrays.toString(questionTF.getTopics()), Arrays.toString(new String[]{"Topic1", "Topic2", "Topic3"}), "Topics are not accurate"),
                () -> assertEquals(Arrays.toString(questionMC.getTopics()), Arrays.toString(new String[]{"Topic"}), "Topics are not accurate"),
                () -> assertEquals(Arrays.toString(questionSA.getTopics()), Arrays.toString(new String[]{}), "Topics are not accurate")
        );
    }
}
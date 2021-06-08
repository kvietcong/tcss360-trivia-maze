package test;

import database.TriviaBase;
import database.TriviaDatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.Question;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TriviaTest {

    TriviaBase triviaBase;

    @BeforeEach
    void setUp() {
        triviaBase = new TriviaBase(
                TriviaDatabaseConnection.getConnectionFromPath(
                        "./resources/trivia-questions-test.txt",
                        "./resources/test.db"
                )
        );
    }

    @AfterEach
    void tearDown() {
        triviaBase.refresh();
    }

    @Test
    void doesNotGiveDuplicates() {
        Set<Question> given = new HashSet<>();
        int duplicateCount = 0;
        // Get [size] # of questions
        while (triviaBase.size() > 0) {
            Question q = triviaBase.getRandomQuestion();
            if (given.contains(q)) {
                duplicateCount++;
            }
            given.add(q);
        }
        assertEquals(0, duplicateCount, duplicateCount + " duplicates found.");
    }

    @Test
    void givesDuplicatesOnOverflowPickingOneAtATime() {
        Set<Question> given = new HashSet<>();
        int duplicateCount = 0;
        // Get [size + 1] # of questions
        int size = triviaBase.size();
        for (int i = 0; i < size + 1; i++) {
            Question q = triviaBase.getRandomQuestion();
            if (given.contains(q)) {
                duplicateCount++;
            }
            given.add(q);
        }
        assertEquals(1, duplicateCount, duplicateCount + " duplicates found instead of 1.");
    }

    @Test
    void givesDuplicatesOnOverflowPickingMultipleAtATime() {
        Set<Question> given = new HashSet<>();
        int duplicateCount = 0;
        // Get [size + 1] # of questions
        List<Question> questions = triviaBase.getRandomQuestions(triviaBase.size() + 1);
        for (Question q : questions) {
            if (given.contains(q)) {
                duplicateCount++;
            }
            given.add(q);
        }
        assertEquals(1, duplicateCount, duplicateCount + " duplicates found instead of 1.");
    }

    @Test
    void getRandomQuestion() {
        assertNotNull(triviaBase.getRandomQuestion(), "Null Question found received.");
    }

    @Test
    void getRandomQuestions() {
        List<Question> questions = triviaBase.getRandomQuestions(10);
        for (Question q : questions) {
            assertNotNull(q, "Null Question found received.");
        }
    }

    @Test
    void refresh() {
        // Empty the pool
        triviaBase.getRandomQuestions(triviaBase.size());
        // Refresh the pool
        triviaBase.refresh();
        assertTrue(triviaBase.size() > 0, "Empty pool does not refresh when asked.");
    }

    @Test
    void refreshesOnOverflow() {
        // Deplete the pool
        triviaBase.getRandomQuestions(triviaBase.size());
        assertEquals(0, triviaBase.size(), "Size not 0 after depleting question pool.");
        Question q = triviaBase.getRandomQuestion();
        assertTrue(triviaBase.size() > 0 || (triviaBase.size() == 0 && q != null), "Empty pool does not refresh on asking for question.");
    }
}

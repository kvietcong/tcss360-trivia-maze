package database;

import question.Question;
import question.QuestionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Serves as a base of trivia Questions that can be retrieved.
 * Retrieving questions removes them from the base until the base is refreshed in order
 * to prevent duplicates.
 */
public class TriviaBase {
    private static final Random RANDOM = new Random();

    private final List<Map<String, String>> questionsInfo;
    private final List<Question> questions;
    private List<Question> currentQuestions;

    /**
     * Creates a TriviaBase with the given connection to a trivia database.
     * @param tdbc Connection to the trivia database.
     */
    public TriviaBase(TriviaDatabaseConnection tdbc) {
        this.questionsInfo = tdbc.getData().get("questions");
        this.questions = readQuestions(this.questionsInfo);
        this.currentQuestions = readQuestions(this.questionsInfo);
    }

    /**
     * Reads the questions from the format given by the TriviaDatabaseConnection and
     * converts them into Question objects.
     * @param questionsInfo Question data in TriviaDatabaseConnection format
     * @return List of Question objects
     */
    private List<Question> readQuestions(List<Map<String, String>> questionsInfo) {
        List<Question> questionsList = new ArrayList<>();
        for (Map<String, String> q : questionsInfo) {
            questionsList.add(
                    QuestionFactory.createQuestion(
                        q.get("question"),
                        q.get("topics").split("\\|"),
                        q.get("choices").split("\\|"),
                        q.get("answer"),
                        q.get("type")
                    )
            );
        }
        return questionsList;
    }

    /**
     * Returns a single random question and removes it from the TriviaBase
     * @return Random Question object
     */
    public Question getRandomQuestion() {
        checkForRefresh(1);
        int index = RANDOM.nextInt(currentQuestions.size());
        Question remove = currentQuestions.get(index);
        currentQuestions.remove(index);
        return remove;
    }

    /**
     * Returns n random questions and removes them from the TriviaBase
     * @param n Number of questions to retrieve
     * @return List of random Question objects
     */
    public List<Question> getRandomQuestions(int n) {
        checkForRefresh(n);
        List<Question> questionsList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // In case of small question pools, this ensures that the pool will refresh until the requested
            // number of questions have been selected.
            checkForRefresh(n - i);
            int index = RANDOM.nextInt(currentQuestions.size());
            Question remove = currentQuestions.get(index);
            currentQuestions.remove(index);
            questionsList.add(remove);
        }
        return questionsList;
    }

    /**
     * Refreshes the question pool if the number of questions in the current question pool is less than n.
     * @param n Minimum number of questions required.
     */
    private void checkForRefresh(int n) {
        if (this.currentQuestions.size() < n) {
            refresh();
        }
    }

    /**
     * Refreshes the questions in the TriviaBase to its original state.
     */
    public void refresh() {
        this.currentQuestions = readQuestions(this.questionsInfo);
    }

    /**
     * Returns the number of questions in the pool.
     * @return The number of questions in the pool.
     */
    public int size() {
        return currentQuestions.size();
    }

    /**
     * Testing method for TriviaBase class
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        TriviaBase tb = new TriviaBase(TriviaDatabaseConnection.getConnection());
        List<Question> questions = tb.getRandomQuestions(3);
        System.out.println(questions);
        System.out.println();
        questions = tb.getRandomQuestions(1);
        System.out.println(questions);
        System.out.println();
        questions = tb.getRandomQuestions(4);
        System.out.println(questions);
    }
}

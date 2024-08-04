package gov.trivia.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class QuestionLoaderTest {

    private static final String QUESTION_FILE_PATH = "data/150questions.csv";

    @Before
    public void setUp() throws Exception {
        // Ensure the questions file exists(I did not rename the file from 150questions to 200questions.csv with the addition of cat4
        assertTrue(Files.exists(Path.of(QUESTION_FILE_PATH)));
    }

    @Test
    public void testLoadQuestions() {
        // Load the questions
        Map<Category, List<Question>> questionMap = QuestionLoader.loadQuestions();

        // Ensure the map is not null and contains the expected categories
        assertNotNull(questionMap);
        assertTrue(questionMap.containsKey(Category.SPORTS));
        assertTrue(questionMap.containsKey(Category.HISTORY));
        assertTrue(questionMap.containsKey(Category.MUSIC));
        assertTrue(questionMap.containsKey(Category.JAYISMS));

        // Check that questions are loaded into all 4 categories the correct categories
        List<Question> sportsQuestions = questionMap.get(Category.SPORTS);
        assertNotNull(sportsQuestions);
        assertFalse(sportsQuestions.isEmpty());

        List<Question> historyQuestions = questionMap.get(Category.HISTORY);
        assertNotNull(historyQuestions);
        assertFalse(historyQuestions.isEmpty());

        // Verify the choices and the correct answer exist
        Question sportsQuestion = sportsQuestions.get(0);
        List<Choice> sportsChoices = sportsQuestion.getChoices();
        assertEquals(4, sportsChoices.size());

        // Verify that one of the (answer)choices is marked as correct---indicated by a *
        boolean correctChoiceFound = false;
        for (Choice choice : sportsChoices) {
            if (choice.isCorrect()) {
                correctChoiceFound = true;
                break;
            }
        }
        assertTrue(correctChoiceFound);

        // Verify the questions are shuffled/randomized.
        List<Question> shuffledQuestions = QuestionLoader.loadQuestions().get(Category.SPORTS);
        assertNotNull(shuffledQuestions);
        assertFalse(shuffledQuestions.isEmpty());
        assertNotEquals(sportsQuestions, shuffledQuestions);  // verify questions are shuffled
    }
}

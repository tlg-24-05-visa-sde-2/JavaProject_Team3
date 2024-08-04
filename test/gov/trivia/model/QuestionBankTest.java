package gov.trivia.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class QuestionBankTest {
    private QuestionBank questionBank;

    @Before
    public void setUp() throws Exception {
        questionBank = new QuestionBank();
    }

    @Test
    public void testQuestionBank_loadedCorrectly() {
        // Let's make sure we have 4 categories: Sports, History, Music, and Jayisms.
        questionBank = new QuestionBank();
        Map<Category, List<Question>> questionMap = questionBank.getQuestionMap(); // Jay said to use getQuestionMap.
        assertNotNull(questionMap);
        assertEquals(4, questionMap.size()); // Jay said to use getQuestionMap.
    }

    @Test
    public void testQuizWizardNextQuestionRemovesAndReturnsQuestion() {
        // We need to check if we can get and remove a question.
        questionBank = new QuestionBank();
        Map<Category, List<Question>> questionMap = questionBank.getQuestionMap(); // Jay said to use getQuestionMap.
        Category category = Category.SPORTS;
        List<Question> questions = questionMap.get(category);
        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        int initialSize = questions.size();

        Question nextQuestion = questionBank.nextQuestion(category);

        // How can we know if our list get smaller by 1--double check the nextQuestion method?
        assertEquals(initialSize - 1, questions.size());

        assertNotNull(nextQuestion);
    }

    @Test
    public void testQuizWizardNextQuestionHandlesEmptyList() {
        // Jay said we need to know what happens if there are no questions left?--not finished
        questionBank = new QuestionBank();
        Category category = Category.SPORTS;
        List<Question> questions = questionBank.getQuestionMap().get(category); // Jay said to use getQuestionMap.
        questions.clear();

        // Verify the list is empty
        assertTrue(questions.isEmpty());

        // It should throw an error if there are no questions.
        try {
            questionBank.nextQuestion(category);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            // Does the error message match?
            String expectedMessage = "No more questions in this category!";
            String actualMessage = e.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    public void testQuizWizardGetQuestionMap() {
        // Make sure we have all four categories.
        questionBank = new QuestionBank();
        Map<Category, List<Question>> questionMap = questionBank.getQuestionMap(); // Jay said to use getQuestionMap.
        assertNotNull(questionMap);

        // Now we are using Sports, History, Music, and Jayisms?
        assertTrue(questionMap.containsKey(Category.SPORTS));
        assertTrue(questionMap.containsKey(Category.HISTORY));
        assertTrue(questionMap.containsKey(Category.MUSIC));
        assertTrue(questionMap.containsKey(Category.JAYISMS));
    }
}

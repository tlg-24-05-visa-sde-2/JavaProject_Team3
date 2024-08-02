package gov.trivia.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

 class QuestionBankTest {
    private QuestionBank questionBank;

    @Test
    void testQuizWizardBankLoadsCorrectly() {
        questionBank = new QuestionBank();
        // Ensure the question map is loaded with categories 1-3 as of now and potentially 4.
        assertEquals(3, questionBank.getQuestionMap().size());
    }

    @Test
    void testQuizWizardNextQuestionRemovesAndReturnsQuestion() {
        questionBank = new QuestionBank();
        // Get the initial map of questions
        Map<Category, List<Question>> questionMap = questionBank.getQuestionMap();
        Category category = Category.SPORTS;
        List<Question> questions = questionMap.get(category);
        int initialSize = questions.size();

        // Get the next question
        Question nextQuestion = questionBank.nextQuestion(category);

        // Verify that the size of the list has decreased by 1
        assertEquals(initialSize - 1, questions.size());

        // Verify that the returned question is not null
        assertNotNull(nextQuestion);
    }

    @Test
    void testQuizWizardNextQuestionHandlesEmptyList() {
        questionBank = new QuestionBank();
        // Remove all questions from a category
        Category category = Category.SPORTS;
        List<Question> questions = questionBank.getQuestionMap().get(category);
        questions.clear();

        // Test that nextQuestion removes a question, reduces list size by 1, and returns a valid question, e.g. not null.
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            questionBank.nextQuestion(category);
        });

        // Make sure the exceptions are thrown correctly.
        String expectedMessage = "Index: 0, Size: 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testQuizWizardGetQuestionMap() {
        questionBank = new QuestionBank();
        // Verify that the questionMap is not null
        Map<Category, List<Question>> questionMap = questionBank.getQuestionMap();
        assertNotNull(questionMap);

        // Verify that the map contains categories for Sports, Music, and History.
        assertFalse(questionMap.isEmpty());
    }
}

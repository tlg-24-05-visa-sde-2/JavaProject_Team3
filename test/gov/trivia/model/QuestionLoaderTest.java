package gov.trivia.model;

import gov.trivia.model.Category;
import gov.trivia.model.Question;
import gov.trivia.model.QuestionLoader;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionLoaderTest {

    @Test
    void testQuizWizardValidCategories() {
        // Ensure the file exists for the 150 questions.
        Path testFilePath = Path.of("data/150questions.csv");
        assertTrue(Files.exists(testFilePath), "Test file should exist at: " + testFilePath.toString());

        Map<Category, List<Question>> questions = QuestionLoader.loadQuestions();

        // Verify that only valid categories are used
        Set<Category> expectedCategories = EnumSet.of(Category.SPORTS, Category.MUSIC, Category.HISTORY, Category.JAYISMS);
        assertTrue(questions.keySet().stream().allMatch(expectedCategories::contains), "All categories should be valid.");
    }

    @Test
    void testQuizWizardNoDuplicateQuestions() {
        // Ensure the file exists for the trivia test file.
        Path testFilePath = Path.of("data/150questions.csv");
        assertTrue(Files.exists(testFilePath), "Test file should exist at: " + testFilePath.toString());

        Map<Category, List<Question>> questions = QuestionLoader.loadQuestions();

        // Check that there are no duplicate questions within each category.
        for (List<Question> questionList : questions.values()) {
            Set<String> questionTexts = new HashSet<>();
            for (Question question : questionList) {
                assertTrue(questionTexts.add(question.getQuestionText()), "Duplicate question found: " + question.getQuestionText());
            }
        }
    }
}

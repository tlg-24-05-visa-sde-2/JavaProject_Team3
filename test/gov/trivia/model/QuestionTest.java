package gov.trivia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import gov.trivia.model.Category;
import gov.trivia.model.Choice;
import gov.trivia.model.Question;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class QuestionTest {

    @Test
    public void testQuizWizardQuestionInitialization() {
        // Verify that the question is correctly initialized from History and that its getters return the expected info.

        Category category = Category.HISTORY;
        String questionText = "What is the capital of France?";
        List<Choice> choices = Arrays.asList(new Choice("Paris", true), new Choice("London", false));

        Question question = new Question(category, questionText, choices);


        assertNotNull(question);
        assertEquals(category, question.getCategory());
        assertEquals(questionText, question.getQuestionText());
        assertEquals(choices, question.getChoices());
    }
    @Test
    public void testQuizWizardGetters() {
        // Verify that the question is correctly initialized from History and that its getters return the expected info.

        Category category = Category.MUSIC;
        String questionText = "Who is known as the King of Pop?";
        List<Choice> choices = Arrays.asList(new Choice("Michael Jackson", true),
                new Choice("Elvis Presley", false));

        Question question = new Question(category, questionText, choices);


        assertEquals(category, question.getCategory());
        assertEquals(questionText, question.getQuestionText());
        assertEquals(choices, question.getChoices());
        assertEquals(choices, question.getOptions());
    }

}

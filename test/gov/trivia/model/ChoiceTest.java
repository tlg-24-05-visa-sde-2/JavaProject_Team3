package gov.trivia.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChoiceTest {
    // Testing the Choice class makes sure the right answers for trivia questions are stored and retrieved correctly.

    @Test
    public void testConstructor() {
        Choice choice = new Choice("Option 1", true);
        assertEquals("Option 1", choice.getOptionText());
        assertTrue(choice.isCorrect());
    }

    @Test
    public void testGettersAndSetters() {
        Choice choice = new Choice("Option 1", true);


        choice.setOptionText("Option 2");
        assertEquals("Option 2", choice.getOptionText());


        choice.setCorrect(false);
        assertFalse(choice.isCorrect());
    }
}

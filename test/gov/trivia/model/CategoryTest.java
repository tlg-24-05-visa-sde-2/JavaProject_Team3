package gov.trivia.model;

import org.junit.Test;
import static org.junit.Assert.*;

// Testing is needed to ensure the category IDs, names, and methods to find categories by ID or name work correctly, including handling invalid values.




public class CategoryTest {

    @Test
    public void testQuizWizardsTriviaGetId() {
        assertEquals(1, Category.SPORTS.getId());
        assertEquals(2, Category.HISTORY.getId());
        assertEquals(3, Category.MUSIC.getId());
        assertEquals(4, Category.JAYISMS.getId());
    }

    @Test
    public void testQuizWizardsTriviaFromId() {
        assertEquals(Category.SPORTS, Category.fromId(1));
        assertEquals(Category.HISTORY, Category.fromId(2));
        assertEquals(Category.MUSIC, Category.fromId(3));
        assertEquals(Category.JAYISMS, Category.fromId(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuizWizardsTriviaFromIdInvalid() {
        Category.fromId(5);
    }

    @Test
    public void testQuizWizardsTriviaGetName() {
        assertEquals("SPORTS", Category.SPORTS.getName());
        assertEquals("HISTORY", Category.HISTORY.getName());
        assertEquals("MUSIC", Category.MUSIC.getName());
        assertEquals("JAYISMS", Category.JAYISMS.getName());
    }

    @Test
    public void testQuizWizardsTriviaFromName() {
        assertEquals(Category.SPORTS, Category.fromName("SPORTS"));
        assertEquals(Category.HISTORY, Category.fromName("HISTORY"));
        assertEquals(Category.MUSIC, Category.fromName("MUSIC"));
        assertEquals(Category.JAYISMS, Category.fromName("JAYISMS"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuizWizardsTriviaFromNameInvalid() {
        Category.fromName("INVALID");
    }

    @Test
    public void testQuizWizardsTriviaListAllCategories() {
        String expectedCategories = "SPORTS, HISTORY, MUSIC, JAYISMS";
        assertEquals(expectedCategories, Category.listAllCategories());
    }
}

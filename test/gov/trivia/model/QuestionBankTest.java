package gov.trivia.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionBankTest {
    @Test
    public void testQuestionBank_loadedCorrectly() {
        QuestionBank questionBank = new QuestionBank();
        assertEquals(3, questionBank.getQuestionMap().size());
    }
}
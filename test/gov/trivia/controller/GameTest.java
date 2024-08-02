package gov.trivia.controller;

import gov.trivia.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testQuestionTimeout() {

        Scanner scanner = new Scanner("A\n");
        Game game = new Game(scanner);
        game.initializeGame();
        QuestionBank questionBank = game.questionBank;

        // Test category 1 question-- Sports
        Question question = questionBank.nextQuestion(Category.SPORTS);
        game.askQuestion(question);

        // Validate the promptForChoice method completes in 30s returning a question from cat1.
        Boolean result = game.promptForChoice(question);


        assertNotNull(result);
    }
}

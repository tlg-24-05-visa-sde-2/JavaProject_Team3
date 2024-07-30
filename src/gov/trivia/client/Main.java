package gov.trivia.client;

import gov.trivia.controller.Game;
import gov.trivia.model.Category;
import gov.trivia.model.Question;
import gov.trivia.model.QuestionBank;
import gov.trivia.model.Choice;

import java.util.List;

class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.execute();
        QuestionBank bank = new QuestionBank();
        Question question = bank.nextQuestion(Category.SPORTS);
        System.out.println(question.getQuestionText());
        List<Choice> options = question.getOptions();
        for (Choice choice : options) {
            if (choice.isCorrect()) {
                System.out.print("Correct - ");
                System.out.println(choice.getOptionText());
            } else {
                System.out.println(choice.getOptionText());
            }

        }
    }
}
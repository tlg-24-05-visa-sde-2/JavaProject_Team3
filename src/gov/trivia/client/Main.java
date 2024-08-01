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
    }
}
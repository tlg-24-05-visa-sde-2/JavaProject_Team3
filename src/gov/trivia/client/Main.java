package gov.trivia.client;

import gov.trivia.controller.Game;
import gov.trivia.model.QuestionLoader;

class Main {
    public static void main(String[] args) {
        QuestionLoader loader = new QuestionLoader();
        loader.loadQuestions();
    }
}
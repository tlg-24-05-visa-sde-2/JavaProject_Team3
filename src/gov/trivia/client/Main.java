package gov.trivia.client;

import gov.trivia.controller.Game;
import gov.trivia.model.QuestionLoader;

class Main {
    public static void main(String[] args) {
        System.out.println(QuestionLoader.loadQuestions());
    }
}
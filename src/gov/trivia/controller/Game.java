package gov.trivia.controller;

import com.apps.util.Console;
import gov.trivia.model.*;
import gov.trivia.model.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

// all the logic for running the game
public class Game {
    private Player player;
    private QuestionBank questionBank;
    private Collection<Category> availableCategories;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectAnswers = 0;


    public void execute() {
        initializeGame();
        playRound();

    }

    private void initializeGame() {
        System.out.println("Loading questions...");
        loadQuestions();
        System.out.println("Done!");

        System.out.println("Welcome to QuizWiz! Please enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        player = new Player(name);

        Category[] categories = Category.values();
        for(int i = 0; i < categories.length - 1; i++){
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.println("Welcome " + name + ". Please pick a category: ");
        displayCategories();

        String input = scanner.nextLine();
        Category category = Category.valueOf(input);

        QuestionBank questionBank = new QuestionBank();
        Question question = null;
        switch (input) {
            case "1" -> question = questionBank.nextQuestion(Category.SPORTS);
            case "2" -> question = questionBank.nextQuestion(Category.HISTORY);
            case "3" -> question = questionBank.nextQuestion(Category.MUSIC);
            default -> System.out.println("Invalid input");
        }

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

    private void playRound() {
        boolean roundOver = false;

        loadQuestions();
        welcome();

        Category category = promptForCategory();
        System.out.println("You have chosen " + category + " - GOOD LUCK!");

        while (!roundOver) {
            Question question = questionBank.nextQuestion(category);
            questionsGiven++;

            String choice = promptForChoice();

            if (question.isChoiceCorrect(choice)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect!");
                incorrectQuestions++;
            }

            roundOver = questionsGiven == 7 || incorrectAnswers == 2;
            availableCategories.remove(category);
            reset();
        }
    }

    private void reset() {
        roundCount++;
        questionsGiven = 0;
        incorrectAnswers = 0;
    }

    private void loadQuestions() {
        questionBank = new QuestionBank();
        availableCategories = new ArrayList<>(questionBank.getQuestionMap().keySet());
    }

    public void welcome() {
        System.out.println("""

                                                                                                                                                                      \s
                                                                                                                                                                      \s
                     QQQQQQQQQ     UUUUUUUU     UUUUUUUUIIIIIIIIIIZZZZZZZZZZZZZZZZZZZ     WWWWWWWW                           WWWWWWWWIIIIIIIIIIZZZZZZZZZZZZZZZZZZZ    \s
                   QQ:::::::::QQ   U::::::U     U::::::UI::::::::IZ:::::::::::::::::Z     W::::::W                           W::::::WI::::::::IZ:::::::::::::::::Z    \s
                 QQ:::::::::::::QQ U::::::U     U::::::UI::::::::IZ:::::::::::::::::Z     W::::::W                           W::::::WI::::::::IZ:::::::::::::::::Z    \s
                Q:::::::QQQ:::::::QUU:::::U     U:::::UUII::::::IIZ:::ZZZZZZZZ:::::Z      W::::::W                           W::::::WII::::::IIZ:::ZZZZZZZZ:::::Z     \s
                Q::::::O   Q::::::Q U:::::U     U:::::U   I::::I  ZZZZZ     Z:::::Z        W:::::W           WWWWW           W:::::W   I::::I  ZZZZZ     Z:::::Z      \s
                Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I          Z:::::Z           W:::::W         W:::::W         W:::::W    I::::I          Z:::::Z        \s
                Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I         Z:::::Z             W:::::W       W:::::::W       W:::::W     I::::I         Z:::::Z         \s
                Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I        Z:::::Z               W:::::W     W:::::::::W     W:::::W      I::::I        Z:::::Z          \s
                Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I       Z:::::Z                 W:::::W   W:::::W:::::W   W:::::W       I::::I       Z:::::Z           \s
                Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I      Z:::::Z                   W:::::W W:::::W W:::::W W:::::W        I::::I      Z:::::Z            \s
                Q:::::O  QQQQ:::::Q U:::::D     D:::::U   I::::I     Z:::::Z                     W:::::W:::::W   W:::::W:::::W         I::::I     Z:::::Z             \s
                Q::::::O Q::::::::Q U::::::U   U::::::U   I::::I  ZZZ:::::Z     ZZZZZ             W:::::::::W     W:::::::::W          I::::I  ZZZ:::::Z     ZZZZZ    \s
                Q:::::::QQ::::::::Q U:::::::UUU:::::::U II::::::IIZ::::::ZZZZZZZZ:::Z              W:::::::W       W:::::::W         II::::::IIZ::::::ZZZZZZZZ:::Z    \s
                 QQ::::::::::::::Q   UU:::::::::::::UU  I::::::::IZ:::::::::::::::::Z               W:::::W         W:::::W          I::::::::IZ:::::::::::::::::Z    \s
                   QQ:::::::::::Q      UU:::::::::UU    I::::::::IZ:::::::::::::::::Z                W:::W           W:::W           I::::::::IZ:::::::::::::::::Z    \s
                     QQQQQQQQ::::QQ      UUUUUUUUU      IIIIIIIIIIZZZZZZZZZZZZZZZZZZZ                 WWW             WWW            IIIIIIIIIIZZZZZZZZZZZZZZZZZZZ    \s
                             Q:::::Q                                                                                                                                  \s
                              QQQQQQ                                                                                                                                  \s
                """);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}



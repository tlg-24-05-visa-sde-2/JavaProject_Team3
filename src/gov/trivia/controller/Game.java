package gov.trivia.controller;

import com.apps.util.Console;
import gov.trivia.model.*;
import gov.trivia.model.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player player;
    private QuestionBank questionBank;
    private Collection<Category> availableCategories;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectAnswers = 0;
    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        initializeGame();
        playRound();
    }

    private void initializeGame() {
        System.out.println("Loading questions...");
        loadQuestions();
        System.out.println("Done!");

        System.out.print("Welcome to QuizWiz! Please enter your name: ");
        String name = scanner.nextLine();
        player = new Player(name);

        System.out.println("Welcome " + name + ". Please pick a category: ");
        displayCategories();
    }

    private void playRound() {
        boolean roundOver = false;

        while (!roundOver) {
            Category category = promptForCategory();
            System.out.println("You have chosen " + category + " - GOOD LUCK!");

            Question question = questionBank.nextQuestion(category);
            questionsGiven++;

            System.out.println(question.getQuestionText());
            List<Choice> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i).getOptionText());
            }

            int choiceIndex = promptForChoice(options.size()) - 1;
            Choice choice = options.get(choiceIndex);

            if (choice.isCorrect()) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect!");
                incorrectAnswers++;
            }

            roundOver = questionsGiven == 7 || incorrectAnswers == 2;
        }

        System.out.println("Round Over! You answered " + questionsGiven + " questions with " + incorrectAnswers + " incorrect answers.");
        reset();
    }

    private void reset() {
        roundCount++;
        questionsGiven = 0;
        incorrectAnswers = 0;
        availableCategories = new ArrayList<>(questionBank.getQuestionMap().keySet());
    }

    private void loadQuestions() {
        questionBank = new QuestionBank();
        availableCategories = new ArrayList<>(questionBank.getQuestionMap().keySet());
    }

    private void displayCategories() {
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
    }

    private Category promptForCategory() {
        Category category = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Please enter the number of your desired category: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                int categoryIndex = Integer.parseInt(input) - 1;
                if (categoryIndex >= 0 && categoryIndex < Category.values().length) {
                    category = Category.values()[categoryIndex];
                    validInput = true;
                }
            }
            if (!validInput) {
                System.out.println("Opps that is an invalid input. Please try again.");
            }
        }

        return category;
    }

    private int promptForChoice(int numberOfOptions) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Please enter the number of your choice: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= numberOfOptions) {
                    validInput = true;
                }
            }
            if (!validInput) {
                System.out.println("Opps that is an invalid input. Please try again");
            }
        }

        return choice;
    }

    private void welcome() {
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

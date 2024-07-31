package gov.trivia.controller;

import com.apps.util.Console;
import gov.trivia.model.*;
import gov.trivia.model.Category;

import java.util.*;

// All the logic for running the game
public class Game {
    private Player player;
    private QuestionBank questionBank;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectRoundAnswers = 0;
    private int failedRounds = 0;

    public void execute() {
        boolean gameOver = false;

        welcome();
        initializeGame();

        while (!gameOver) {
            if (failedRounds > 1) {
                System.out.println("Sorry, you missed two questions in two different rounds! Please try again.");
                break;
            }

            gameOver = roundCount == 3;
            playRound();
            reset();
            roundCount++;
        }

        if (failedRounds < 2) {
            System.out.println("You win!");
            System.out.println("Continue?");
        }
    }

    private void initializeGame() {
        System.out.println("Loading questions...");
        loadQuestions();
        System.out.println("Let's Play Some Trivia!");

        System.out.println("Welcome to QuizWiz! Please enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        player = new Player(name);

        questionBank = new QuestionBank();
    }

    private void askQuestion(Question question) {
        String questionText = question.getQuestionText();
        System.out.println(questionText);
        System.out.println("-".repeat(questionText.length()));
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean promptForChoice(Question question) {
        clearConsole();
        Scanner scanner = new Scanner(System.in);

        List<Choice> options = question.getOptions();
        String[] choices = {"A", "B", "C", "D"};
        for (int i = 0; i < choices.length; i++) {
            if (i < options.size()) {
                System.out.println(choices[i] + " - " + options.get(i).getOptionText());
            }
        }



        while (true) {
            System.out.println("Enter your guess: ");
            String input = scanner.nextLine();

            if (Arrays.stream(choices).anyMatch(input::equalsIgnoreCase)) {
                int choiceIndex = Arrays.asList(choices).indexOf(input.toUpperCase());

                Choice guess = options.get(choiceIndex);
                return guess.isCorrect();
            } else {
                System.out.println("Invalid input. Please enter A, B, C, or D");
                input = scanner.nextLine();
            }
        }
    }

    private void displayCategories() {
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length - 1; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
    }

    private Category promptForCategory() {
        clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello " + player.getName() + ". Please pick a category 1-4: ");
        displayCategories();

        String input = scanner.nextLine();
        int categoryIndex = -1;
        while (true) {
            try {
                categoryIndex = Integer.parseInt(input);
                if (categoryIndex >= 1 && categoryIndex <= Category.values().length) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + Category.values().length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + Category.values().length);
            }
            input = scanner.nextLine();
        }

        return Category.fromId(categoryIndex);
    }

    private void playRound() {
        boolean roundOver = false;

        Category category = promptForCategory();
        System.out.println("You have chosen " + category + " - Give it your best shot, you've got this!");

        while (!roundOver) {
            Question question = questionBank.nextQuestion(category);
            askQuestion(question);
            questionsGiven++;

            Boolean answer = promptForChoice(question);

            if (answer) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect!");
                incorrectRoundAnswers++;
            }

            roundOver = questionsGiven == 7 || incorrectRoundAnswers == 2;

            if (incorrectRoundAnswers == 2) {
                failedRounds++;
            }
        }
    }

    private void reset() {
        questionsGiven = 0;
        incorrectRoundAnswers = 0;
    }

    private void loadQuestions() {
        questionBank = new QuestionBank();
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
        System.out.println();
        System.out.println(">>Rules<<");
        System.out.println("----------------");
        System.out.println("-Enter your name");
        System.out.println("-Pick a category");
        System.out.println("-2 incorrect answers and the game takes you back to category selection");
        System.out.println("-If you lose 2 rounds of any category the game ends");
        System.out.println("-Have fun!");
        System.out.println("----------------");
        System.out.println();
    }
}

package gov.trivia.controller;


import com.apps.util.Console;
import com.apps.util.Prompter;
import gov.trivia.model.*;
import gov.trivia.model.Category;
import com.apps.util.SplashApp;

import java.util.*;
import java.util.concurrent.*;

public class Game {
    private Player player;
    private QuestionBank questionBank;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectRoundAnswers = 0;
    private int failedRounds = 0;
    private final Scanner scanner = new Scanner(System.in);
    private final long QUESTION_TIME_LIMIT = 20000L; // 20 seconds

    public void execute() {
        boolean gameOver = false;

        //welcome();
        initializeGame();

        while (!gameOver) {
            if (failedRounds > 1) {
                System.out.println();
                break;
            }

            gameOver = roundCount == 3;
            playRound();
            reset();
            roundCount++;
        }

        if (failedRounds < 2) {
            System.out.println("Well done! You’ve won!");
            System.out.println("Do you wish to continue?");
        }
    }

    private void initializeGame() {
        welcome();
        System.out.println("Loading questions...");
        Console.pause(1500);
        loadQuestions();

        System.out.println("Done!");
        Console.pause(1300);
        String name = null;
        while(true){
            System.out.println("Welcome to QuizWiz! Please enter your name: ");
            name = scanner.nextLine();
            if(name.matches("^[a-zA-Z]+$")) {
                break;
            } else {
                System.out.println("Please enter a valid name: ");
            }
        }

        Console.pause(1200);
        Console.blankLines(1);
        player = new Player(name);

        questionBank = new QuestionBank();
    }

    private void askQuestion(Question question) {
        String questionText = question.getQuestionText();
        System.out.println(questionText);
        System.out.println("-".repeat(questionText.length()));
    }

    private Boolean promptForChoice(Question question) {
        List<Choice> options = question.getOptions();
        String[] choices = {"A", "B", "C", "D"};
        for (int i = 0; i < choices.length; i++) {
            System.out.println(choices[i] + " - " + options.get(i).getOptionText());
        }

        System.out.println("Enter your guess (You have 20 seconds!): ");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> scanner.nextLine());
        String input;

        try {
            input = future.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Time's up!");
            future.cancel(true);
            return false;
        } catch (Exception e) {
            future.cancel(true);
            return false;
        } finally {
            executor.shutdown();
        }

        while (true) {
            if (Arrays.stream(choices).anyMatch(input::equalsIgnoreCase)) {
                int choiceIndex = Arrays.asList(choices).indexOf(input.toUpperCase());
                Choice guess = options.get(choiceIndex);

                return guess.isCorrect();
            } else {
                System.out.println("Your input is invalid. Please enter A, B, C, or D.");
                input = scanner.nextLine().trim();
            }
        }
    }

    private void displayCategories() {
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
    }
        // updated promptForCategory method---will change 1-3 once easter egg is implemented.
    private Category promptForCategory() {
        System.out.println("Hello " + player.getName() + ". Please pick a category 1-4: ");
        displayCategories();

        String input = scanner.nextLine();
        Console.pause(1000);
        Console.blankLines(1);

        while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > Category.values().length) {
            System.out.println("Invalid input. Please enter a valid category number.");
            input = scanner.nextLine();
        }

        return Category.fromId(Integer.parseInt(input));
    }

    private void playRound() {
        boolean roundOver = false;

        Category category = promptForCategory();
      
        System.out.println("You have chosen " + category + " -- Good luck, you’ve got this!");
        Prompter prompter = new Prompter(scanner);
        prompter.prompt("Press [Enter] to get started...");
        Console.clear();

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
                if (failedRounds > 1) {
                    System.out.println("You missed 2 questions in two different categories. Please try again!");
                    break;
                }
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
                
               
                ________        .__          __      __.__       \s
                \\_____  \\  __ __|__|_______ /  \\    /  \\__|_______
                 /  / \\  \\|  |  \\  \\___   / \\   \\/\\/   /  \\___   /
                /   \\_/.  \\  |  /  |/    /   \\        /|  |/    /\s
                \\_____\\ \\_/____/|__/_____ \\   \\__/\\  / |__/_____ \\
                       \\__>              \\/        \\/           \\/
                
                
                """);
        System.out.println("---------------------------------------------------------");
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

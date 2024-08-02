package gov.trivia.controller;

import com.apps.util.Console;
import com.apps.util.Prompter;
import gov.trivia.model.*;
import com.apps.util.SplashApp;
import static com.apps.util.Console.*;

import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

import static com.apps.util.SplashApp.DEFAULT_PAUSE;

public class Game {
    private Player player;
    private QuestionBank questionBank;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectRoundAnswers = 0;
    private int failedRounds = 0;
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Scanner scanner = new Scanner(System.in);
    private final long QUESTION_TIME_LIMIT = 20000L; // 20 seconds

    public void execute() {
        boolean gameOver = false;

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

        String name = prompter.prompt("Welcome to QuizWiz! Please enter your name: ", "^[a-zA-Z]+$","\nPlease enter a valid name\n");



//        String name = null;
//        while(true){
//            System.out.println("Welcome to QuizWiz! Please enter your name: ");
//            name = scanner.nextLine();
//            if(name.matches("^[a-zA-Z]+$")) {
//                break;
//            } else {
//                System.out.println("Please enter a valid name: ");
//            }
//        }

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

    private Category promptForCategory() {
//        System.out.println("Hello " + player.getName() + ". Please pick a category 1-4: ");
        displayCategories();

        blankLines(1);
        String input = prompter.prompt("Hello " + player.getName() + ". Please pick a category 1-4: ", "[1-4]", "\nPlease enter a valid category number.\n");
        pause(1000);
        blankLines(1);


//        String input = scanner.nextLine();
//        Console.pause(1000);
//        Console.blankLines(1);
//
//        while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > Category.values().length) {
//            System.out.println("Invalid input. Please enter a valid category number.");
//            input = scanner.nextLine();
//        }

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
                pause(1500);
                clear();
            } else {
                System.out.println("Incorrect!");
                incorrectRoundAnswers++;
                pause(1500);
                clear();
            }

            roundOver = questionsGiven == 7 || incorrectRoundAnswers == 2;

            if (incorrectRoundAnswers == 2) {
                failedRounds++;
                if (failedRounds > 1) {
                    System.out.println("You missed 2 questions in two different categories");
                    blankLines(2);
                    pause(1500);
                    System.out.println("Thank you for playing...");
                    pause(1500);
                    welcome();
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

    public void welcome(String... messages) throws IllegalArgumentException {
        for (String message : messages) {
            System.out.println(message);
            try {
                Thread.sleep(DEFAULT_PAUSE);
            } catch (InterruptedException e) {
                throw new IllegalArgumentException("Error initializing application", e);
            }
        }
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

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                clearConsole();
                break;
            case 2:
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public void welcome() {
        //Files.readString("resources/banner.txt");






//        System.out.println("""
//
//                ________        .__          __      __.__
//                \\_____  \\  __ __|__|_______ /  \\    /  \\__|_______
//                 /  / \\  \\|  |  \\  \\___   / \\   \\/\\/   /  \\___   /
//                /   \\_/.  \\  |  /  |/    /   \\        /|  |/    /
//                \\_____\\ \\_/____/|__/_____ \\   \\__/\\  / |__/_____ \\
//                       \\__>              \\/        \\/           \\/
//
//                """);
//        System.out.println("---------------------------------------------------------");
//        System.out.println();
//        System.out.println(">>Rules<<");
//        System.out.println("----------------");
//        System.out.println("-Enter your name");
//        System.out.println("-Pick a category");
//        System.out.println("-2 incorrect answers and the game takes you back to category selection");
//        System.out.println("-If you lose 2 rounds of any category the game ends");
//        System.out.println("-Have fun!");
//        System.out.println("----------------");
//        System.out.println();
    }
}

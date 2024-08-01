package gov.trivia.controller;

import com.apps.util.Console;
import com.apps.util.Prompter;
import gov.trivia.model.*;
import gov.trivia.model.Category;

import java.util.*;

// all the logic for running the game
public class Game {
    private Player player;
    private QuestionBank questionBank;

    private int roundCount = 1;
    private int questionsGiven = 0;
    private int incorrectRoundAnswers = 0;
    private int failedRounds = 0;


    public void execute() {
        boolean gameOver = false;

        //welcome();
        initializeGame();

        while (!gameOver) {
            if (failedRounds > 1) {
                System.out.println("You lose!");
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
        welcome();
        System.out.println("Loading questions...");
        Console.pause(1500);
        loadQuestions();
        System.out.println("Done!");
        Console.pause(1300);

        System.out.println("Welcome to QuizWiz! Please enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
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
        Scanner scanner = new Scanner(System.in);

        List<Choice> options = question.getOptions();
        String[] choices = {"A", "B", "C", "D"};
        for (int i = 0; i < choices.length; i++) {
            System.out.println(choices[i] + " - " + options.get(i).getOptionText());
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello " + player.getName() + ". Please pick a category: ");
        displayCategories();

        String input = scanner.nextLine();
        Console.pause(1000);
        Console.blankLines(1);

        return Category.fromId(Integer.parseInt(input));
    }

    private void playRound() {
        boolean roundOver = false;
        Scanner scanner = new Scanner(System.in);

        Category category = promptForCategory();
        System.out.println("You have chosen " + category + " - GOOD LUCK!");
        Prompter prompter = new Prompter(scanner);
        prompter.prompt("Press [Enter] to get started...");
        Console.clear();

//        System.out.println("Press [Enter] to continue...");
//        String input = scanner.nextLine();

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



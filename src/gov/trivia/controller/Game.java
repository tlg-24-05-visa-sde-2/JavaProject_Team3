package gov.trivia.controller;

import com.apps.util.Console;
import com.apps.util.Prompter;
import gov.trivia.model.*;
import com.apps.util.SplashApp;
import static com.apps.util.Console.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
            System.out.println("Congratulations, you win!");
            blankLines(2);
            String input = prompter.prompt("Would you like to play again? [y/n]", "[y|n]", "y and n are the only acceptable inputs");
            if(input.equals("y")){
                clear();
                execute();
            } else {
                clear();
                System.out.println("Thank you for playing...");
                pause(1500);
                welcome();
            }
        }
    }

    private void initializeGame() {
        welcome();
        blankLines(1);
        System.out.println("\nConjuring questions...\n");
        pause(1500);
        loadQuestions();

        System.out.println("Done!");
        pause(1300);
        blankLines(1);

        String name = prompter.prompt("Welcome to QuizWiz! Please enter your name: ", "^[a-zA-Z]+$","\nPlease enter a valid name\n");

        Console.pause(1200);
        Console.blankLines(1);
        clear();
        player = new Player(name);
        questionBank = new QuestionBank();
        displayRules();
        prompter.prompt("\nPress [Enter] to get started...");
        clear();
    }

    private void askQuestion(Question question) {
        String questionText = question.getQuestionText();
        System.out.println(questionText);
        System.out.println("-".repeat(questionText.length()));
    }

    private Boolean promptForChoice(Question question) {
        List<Choice> choices = question.getChoices(); // getChoices is now used as recommended by Jay
        String[] options = {"A", "B", "C", "D"};
        boolean isCorrect = false;

        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i] + " - " + choices.get(i).getOptionText());
        }

        blankLines(1);
        System.out.println("Number of missed questions: " + incorrectRoundAnswers);
        String input = prompter.prompt("\nEnter your guess: ", "[a-d]", "Your input is invalid. Please enter A, B, C, or D.");
        blankLines(1);


        if (input != null && Arrays.stream(options).anyMatch(input::equalsIgnoreCase)) {
            int choiceIndex = Arrays.asList(options).indexOf(input.toUpperCase());
            Choice guess = choices.get(choiceIndex);

            if (guess.isCorrect()) {
                isCorrect = true;
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private void displayCategories() {
        categoryBooks();

        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
//            System.out.println((i + 1) + ". " + categories[i]);
        }
    }

    private Category promptForCategory() {
        displayCategories();
        blankLines(2);
        System.out.println("Rounds failed " + failedRounds);
        blankLines(1);
        String input = prompter.prompt("Hello " + player.getName() + ". Please pick a category 1-4: ", "[1-4]", "\nPlease enter a valid category number.\n");
        pause(1000);
        blankLines(1);

        return Category.fromId(Integer.parseInt(input));
    }

    private void playRound() {
        boolean roundOver = false;

        Category category = promptForCategory();

      
        System.out.println("You have chosen " + category + " -- Good luck, you got this!");
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
                    System.out.println("Alas! You've missed 2 questions in two categories. The Quiz Wizard declares: GAME OVER!");
                    blankLines(2);
                    pause(1500);
                    System.out.println("Thanks for playing!");
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

    public void welcome() {
        try (FileReader reader = new FileReader("resources/banner.txt")) {
            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayRules() {
        try (FileReader reader = new FileReader("resources/rules.txt")) {
            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void categoryBooks() {
        try(FileReader reader = new FileReader("resources/catbooks.txt");){
            int data = reader.read();
            while(data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

package gov.trivia.controller;

import gov.trivia.model.*;
import gov.trivia.model.Category;
import com.apps.util.SplashApp;

import java.util.*;
import java.util.concurrent.*;

public class Game implements SplashApp {
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

        welcome();
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
        System.out.println("Loading questions...");
        loadQuestions();
        System.out.println("Let’s get started!");

        System.out.println("Welcome to QuizWiz! Please enter your name: ");
        String name = scanner.nextLine();
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
        String input = "";

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

    @Override
    public void start() {
        // SplashApp didn't work from the lib
    }

    @Override
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

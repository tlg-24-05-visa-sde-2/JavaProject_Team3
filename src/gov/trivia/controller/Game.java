package gov.trivia.controller;

import gov.trivia.model.Category;
import gov.trivia.model.QuestionLoader;

import java.util.Scanner;

// all the logic for running the game
public class Game {
    public void execute() {
        System.out.println("\n" +
                "                                                                                                                                                       \n" +
                "                                                                                                                                                       \n" +
                "     QQQQQQQQQ     UUUUUUUU     UUUUUUUUIIIIIIIIIIZZZZZZZZZZZZZZZZZZZ     WWWWWWWW                           WWWWWWWWIIIIIIIIIIZZZZZZZZZZZZZZZZZZZ     \n" +
                "   QQ:::::::::QQ   U::::::U     U::::::UI::::::::IZ:::::::::::::::::Z     W::::::W                           W::::::WI::::::::IZ:::::::::::::::::Z     \n" +
                " QQ:::::::::::::QQ U::::::U     U::::::UI::::::::IZ:::::::::::::::::Z     W::::::W                           W::::::WI::::::::IZ:::::::::::::::::Z     \n" +
                "Q:::::::QQQ:::::::QUU:::::U     U:::::UUII::::::IIZ:::ZZZZZZZZ:::::Z      W::::::W                           W::::::WII::::::IIZ:::ZZZZZZZZ:::::Z      \n" +
                "Q::::::O   Q::::::Q U:::::U     U:::::U   I::::I  ZZZZZ     Z:::::Z        W:::::W           WWWWW           W:::::W   I::::I  ZZZZZ     Z:::::Z       \n" +
                "Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I          Z:::::Z           W:::::W         W:::::W         W:::::W    I::::I          Z:::::Z         \n" +
                "Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I         Z:::::Z             W:::::W       W:::::::W       W:::::W     I::::I         Z:::::Z          \n" +
                "Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I        Z:::::Z               W:::::W     W:::::::::W     W:::::W      I::::I        Z:::::Z           \n" +
                "Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I       Z:::::Z                 W:::::W   W:::::W:::::W   W:::::W       I::::I       Z:::::Z            \n" +
                "Q:::::O     Q:::::Q U:::::D     D:::::U   I::::I      Z:::::Z                   W:::::W W:::::W W:::::W W:::::W        I::::I      Z:::::Z             \n" +
                "Q:::::O  QQQQ:::::Q U:::::D     D:::::U   I::::I     Z:::::Z                     W:::::W:::::W   W:::::W:::::W         I::::I     Z:::::Z              \n" +
                "Q::::::O Q::::::::Q U::::::U   U::::::U   I::::I  ZZZ:::::Z     ZZZZZ             W:::::::::W     W:::::::::W          I::::I  ZZZ:::::Z     ZZZZZ     \n" +
                "Q:::::::QQ::::::::Q U:::::::UUU:::::::U II::::::IIZ::::::ZZZZZZZZ:::Z              W:::::::W       W:::::::W         II::::::IIZ::::::ZZZZZZZZ:::Z     \n" +
                " QQ::::::::::::::Q   UU:::::::::::::UU  I::::::::IZ:::::::::::::::::Z               W:::::W         W:::::W          I::::::::IZ:::::::::::::::::Z     \n" +
                "   QQ:::::::::::Q      UU:::::::::UU    I::::::::IZ:::::::::::::::::Z                W:::W           W:::W           I::::::::IZ:::::::::::::::::Z     \n" +
                "     QQQQQQQQ::::QQ      UUUUUUUUU      IIIIIIIIIIZZZZZZZZZZZZZZZZZZZ                 WWW             WWW            IIIIIIIIIIZZZZZZZZZZZZZZZZZZZ     \n" +
                "             Q:::::Q                                                                                                                                   \n" +
                "              QQQQQQ                                                                                                                                   \n");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Welcome to QuizWiz! Please enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for(Category category : Category.values()){
            System.out.println(category);
        }
        System.out.println("Welcome " + name + ". Please pick a category: ");
        // user picks category


        // QuestionLoader loads map up with questions

        // QuestionBank returns a new question
    }
}
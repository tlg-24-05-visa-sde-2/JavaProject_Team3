package gov.trivia.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//SPORTS,What baseball player holds the record for most home runs?,Hank Aaron;Babe Ruth;Barry Bonds*;Jay Rostosky
public class QuestionLoader {
    private static final String questionFilePath = "data/question-data.csv";

    public static Map<String, Question> loadQuestions() {
        try {
            Files.lines(Path.of(questionFilePath)).forEach(line -> {  // block lambda
                String[] tokens = line.split(",");

                String category = tokens[0];
                String question = tokens[1];
                String[] choiceArray = tokens[2].split(";");
                List<String> choices = new ArrayList<>(Arrays.asList(choiceArray));
                System.out.println(category);
                System.out.println(question);
                System.out.println(choices);
            });
        }
        catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//- reads all questions from questions.csv, shuffles each question's choices (so the right answer isn't always C, for example)
// - shuffles them, picks 7 from each category, and inserts them into QuestionBank
}
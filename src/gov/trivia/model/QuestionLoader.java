package gov.trivia.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//SPORTS,What baseball player holds the record for most home runs?,Hank Aaron;Babe Ruth;Barry Bonds*;Jay Rostosky
public class QuestionLoader {
    private static final String questionFilePath = "data/150questions.csv";

    public static Map<Category, List<Question>> loadQuestions() {
        Map<Category, List<Question>> questions = new HashMap<>();

        try {
            Files.lines(Path.of(questionFilePath)).forEach(line -> {
                String[] tokens = line.split(",");

                Category category = Category.valueOf(tokens[0].toUpperCase());
                String questionText = tokens[1];
                String[] choiceArray = tokens[2].split(";");
                List<Choice> choices = new ArrayList<>();
                for (String choiceText : choiceArray) {
                    boolean isCorrect = choiceText.endsWith("*");
                    if (isCorrect) {
                        choiceText = choiceText.substring(0, choiceText.length() - 1);
                    }
                    choices.add(new Choice(choiceText, isCorrect));
                }

                Collections.shuffle(choices); // don't test for this since it is randomized

                Question question = new Question(category, questionText, choices);
                questions.computeIfAbsent(category, k -> new ArrayList<>()).add(question);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        questions.values().forEach(Collections::shuffle); // don't test for this since it is randomized

        return questions;
    }
}

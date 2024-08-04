package gov.trivia.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class QuestionLoader {
    private static final String questionFilePath = "data/questions.csv";

    public static Map<Category, List<Question>> loadQuestions() {
        Map<Category, List<Question>> questions = new HashMap<>();

        try {
            Files.lines(Path.of(questionFilePath)).forEach(line -> {  // block lambda
                String[] tokens = line.split(",");

                Category category = Category.valueOf(tokens[0].toUpperCase());
                String question = tokens[1];
                String[] choiceArray = tokens[2].split(";");
                List<Choice> choices = new ArrayList<>();
                for (String choice : choiceArray) {
                    Choice choiceObj;
                    if (choice.endsWith("*")) {
                        choiceObj = new Choice(choice.substring(0, choice.length() - 1), true);
                    } else {
                        choiceObj = new Choice(choice.substring(0, choice.length()), false);
                    }
                    choices.add(choiceObj);
                }

                Collections.shuffle(choices);

                Question questionObj = new Question(category, question, choices);

                if (questions.containsKey(questionObj.getCategory())) {
                    questions.get(questionObj.getCategory()).add(questionObj);
                } else {
                    List<Question> questionList = new ArrayList<>();
                    questionList.add(questionObj);
                    questions.put(questionObj.getCategory(), questionList);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Category, List<Question>> entry : questions.entrySet()) {
            List<Question> questionList = entry.getValue();
            Collections.shuffle(questionList);
        }

        return questions;
    }
}

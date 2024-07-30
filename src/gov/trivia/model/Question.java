package gov.trivia.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public Category category;
    public String questionText;
    public List<Choice> options;

    public Question(Category category, String questionText, List<Choice> options) {
        setCategory(category);
        setQuestionText(questionText);
        setOptions(options);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Choice> getOptions() {
        return options;
    }

    public void setOptions(List<Choice> options) {
        this.options = options;
    }
}
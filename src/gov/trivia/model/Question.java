package gov.trivia.model;

import java.util.List;

public class Question {
    private Category category;
    private String questionText;
    private List<Choice> choices;

    public Question(Category category, String questionText, List<Choice> choices) {
        this.category = category;
        this.questionText = questionText;
        this.choices = choices;
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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getOptions() {
        return choices;
    }
}

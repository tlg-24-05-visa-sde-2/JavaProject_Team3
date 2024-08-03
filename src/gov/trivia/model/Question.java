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

    public String getQuestionText() {
        return questionText;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getQuestionText()).append(": \n");
        for (Choice choice : getChoices()) {
            builder.append(choice.getOptionText()).append("=").append(choice.isCorrect()).append("\n");
        }
        return builder.toString();
    }
}

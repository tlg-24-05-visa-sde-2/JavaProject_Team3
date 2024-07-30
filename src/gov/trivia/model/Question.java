package gov.trivia.model;

public class Question {
    private String questionText;
    private String[] choices;
    private int correctOptionKey;

    public Question(String questionText, String[] choices, int correctOptionKey) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctOptionKey = correctOptionKey;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public boolean isChoiceCorrect(String choice) {
        return choices[correctOptionKey].equals(choice);
    }
}

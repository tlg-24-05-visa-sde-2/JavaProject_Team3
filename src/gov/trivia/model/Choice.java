package gov.trivia.model;

public class Choice {
    public char option;
    public String optionText;
    public boolean isCorrect;

    public Choice(String optionText, boolean isCorrect) {
        setOptionText(optionText);
        setCorrect(isCorrect);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
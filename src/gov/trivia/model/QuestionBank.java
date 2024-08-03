package gov.trivia.model;

import java.util.List;
import java.util.Map;

public class QuestionBank {

    private Map<Category, List<Question>> questionMap = QuestionLoader.loadQuestions();

    // We need to get the next question and remove it from the list.
    // But be careful, if there are no questions left, it might crash---we need to test this before Sunday.
    public Question nextQuestion(Category category) {
        List<Question> questions = questionMap.get(category);
        if (questions.isEmpty()) {
            throw new IndexOutOfBoundsException("No more questions in this category!");
        }
        return questions.remove(0);
    }

    // Jay said to use getQuestionMap to see all our questions.
    Map<Category, List<Question>> getQuestionMap() {
        return questionMap;
    }
}

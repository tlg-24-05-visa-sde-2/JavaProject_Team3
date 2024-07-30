package gov.trivia.model;

import java.util.List;
import java.util.Map;

class QuestionBank {
    // Implement QuestionLoader to load this map
    Map<Category, List<Question>> questionMap;

    // remove() from list which returns a Question
    // handle when remove() has no more Question(s) to remove
    public Question nextQuestion(Category category) {
        return null;
    }


}
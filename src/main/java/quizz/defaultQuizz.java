package quizz;

import questions.Question;
import user.User;
import java.util.List;

public class defaultQuizz extends quizz {

    // Constructor with id, initializing all fields
    public defaultQuizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(id, name, author, questions, history);
    }

    // Constructor without id, useful for creating new quizzes before saving to database
    public defaultQuizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(name, author, questions, history);
    }

    // Method to get the list of questions in the quiz
    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}

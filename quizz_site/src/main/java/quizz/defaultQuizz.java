package quizz;

import questions.Question;
import user.User;
import java.util.List;

public class defaultQuizz extends quizz{
    public defaultQuizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(id, name, author, questions, history);
    }

    public defaultQuizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(name, author, questions, history);
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
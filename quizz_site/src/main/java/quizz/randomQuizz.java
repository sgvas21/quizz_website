package quizz;

import questions.Question;
import user.User;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class randomQuizz extends quizz{
    public randomQuizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(id, name, author, questions, history);
    }

    public randomQuizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(name, author, questions, history);
    }

    @Override
    public List<Question> getQuestions() {
        Random random = new Random();
        List<Question> result = new ArrayList<>();
        while(questions.size() > 1) {
            int index = random.nextInt(questions.size() - 1);
            result.add(questions.get(index));
            questions.remove(index);
        }
        if (!questions.isEmpty()) result.add(questions.get(0));
        return result;
    }
}
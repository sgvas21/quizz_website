package quizz;

import questions.Question;
import user.User;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class randomQuizz extends quizz {

    // Constructor with id, initializing all fields
    public randomQuizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(id, name, author, questions, history);
    }

    // Constructor without id, useful for creating new quizzes before saving to database
    public randomQuizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        super(name, author, questions, history);
    }

    // Method to randomly shuffle and retrieve questions from the quiz
    @Override
    public List<Question> getQuestions() {
        Random random = new Random();
        List<Question> result = new ArrayList<>();

        // Shuffle questions randomly
        while (questions.size() > 1) {
            int index = random.nextInt(questions.size() - 1);
            result.add(questions.get(index));
            questions.remove(index);
        }

        // Add the remaining question (if any) to the result
        if (!questions.isEmpty()) {
            result.add(questions.get(0));
        }

        return result;
    }
}

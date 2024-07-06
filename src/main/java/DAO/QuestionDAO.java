package DAO;

import questions.Question;


import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO {
    void addQuestion(Question question, long quiz_id) throws SQLException;
    List<Question> getQuestions(long quizId) throws SQLException;
}

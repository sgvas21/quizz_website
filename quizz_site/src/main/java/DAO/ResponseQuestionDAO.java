package DAO;

import questions.Question;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ResponseQuestionDAO implements QuestionDAO {
    private final Connection con;

    public ResponseQuestionDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {

    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        return null;
    }
}

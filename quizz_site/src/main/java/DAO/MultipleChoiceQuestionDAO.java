package DAO;

import database.DBConnection;
import questions.MultipleChoiceQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MultipleChoiceQuestionDAO {
    MultipleChoiceQuestionDAO() {}

    public MultipleChoiceQuestion getQuestion() throws SQLException {
        return null;
    }

    public void addQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws SQLException {
    }

    private MultipleChoiceQuestion convertToQuestionBean(ResultSet resultSet) throws SQLException {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setQuestionId(resultSet.getInt("id"));
        mcq.setQuestion(resultSet.getString("question"));
        mcq.setCorrectAnswer(resultSet.getString("correct_answer"));
        return mcq;
    }
}

package questions;

import DAO.QuestionDAO;
import response.Response;

import java.sql.SQLException;

public interface Question {
    long getQuestionId();
    String getQuestion();
    double getScore(Response response);
    QuestionDAO getDao() throws SQLException, ClassNotFoundException;
}

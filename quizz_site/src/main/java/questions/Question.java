package questions;

import DAO.QuestionDAO;
import response.Response;

import java.sql.SQLException;

public interface Question {
    //int getQuestionId();
    //void setQuestionId(int questionId);
    String getQuestion();
    //void setQuestion(String question);
    double getScore(Response response);
    QuestionDAO getDao() throws SQLException, ClassNotFoundException;
}

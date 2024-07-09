package questions;

import DAO.QuestionDAO;
import response.Response;

import java.sql.SQLException;

/**
 * Interface representing a generic Question.
 * Provides methods to retrieve question ID, question text, calculate score based on response,
 * and obtain a DAO object for database operations.
 */
public interface Question {
    /**
     * Retrieves the ID of the question.
     *
     * @return the question ID
     */
    long getQuestionId();

    /**
     * Retrieves the text of the question.
     *
     * @return the question text
     */
    String getQuestion();

    /**
     * Calculates the score based on the provided response.
     *
     * @param response the response object containing user-provided answers
     * @return the score as a double value
     */
    double getScore(Response response);

    /**
     * Retrieves a DAO object specific to handling operations related to this question.
     *
     * @return the DAO object for handling question operations
     * @throws SQLException            if a database access error occurs
     * @throws ClassNotFoundException  if the database driver class is not found
     */
    QuestionDAO getDao() throws SQLException, ClassNotFoundException;
}

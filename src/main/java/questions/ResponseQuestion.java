package questions;

import DAO.QuestionDAO;
import DAO.ResponseQuestionDAO;
import database.DBConnection;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ResponseQuestion extends ResponseFill_InTheBlankQuestion {
    public ResponseQuestion(String question, List<String> legalAnswers, int id) {
        super(question, legalAnswers, id);
    }
    public ResponseQuestion(String question, List<String> legalAnswers) {
        super(question, legalAnswers);
    }

    /**
     * Calculates the score based on the provided response.
     * It checks each answer in the response against the list of legal answers.
     * If any answer in the response matches a legal answer, returns 1.
     * If no answers match any legal answer, returns 0.
     *
     * @param response the response object containing user-provided answers
     * @return the score as a double value (1.0 for correct, 0.0 for incorrect)
     */
    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        while(iterator.hasNext()) {
            if (getLegalAnswers().contains(iterator.next())) return 1;
        }
        return 0;
    }

    /**
     * Returns a DAO object specific to handling questions.
     * Creates a new instance of ResponseQuestionDAO using a database connection.
     *
     * @return the DAO object for handling questions
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new ResponseQuestionDAO(DBConnection.getConnection());
    }

}

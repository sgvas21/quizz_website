package questions;

import DAO.Fill_InTheBlankQuestionDAO;
import DAO.QuestionDAO;
import database.DBConnection;
import response.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Fill_InTheBlankQuestion extends ResponseFill_InTheBlankQuestion {

    /**
     * Constructor with question, legal answers, and id
     *
     * @param question     the question text
     * @param legalAnswers the list of legal answers
     * @param id           the question ID
     */
    public Fill_InTheBlankQuestion(String question, List<String> legalAnswers, int id) {
        super(question, legalAnswers, id);
    }

    /**
     * Constructor with question and legal answers
     *
     * @param question     the question text
     * @param legalAnswers the list of legal answers
     */
    public Fill_InTheBlankQuestion(String question, List<String> legalAnswers) {
        super(question, legalAnswers);
    }


    /**
     * Calculates the score for the given response
     *
     * @param response the response to the question
     * @return the score as a double value rounded to two decimal places
     */
    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        int count = 0;
        int idx = 0;
        while (iterator.hasNext()) {
            if (getLegalAnswers().get(idx).equals(iterator.next())) count++;
            idx++;
        }

        BigDecimal bd = new BigDecimal((double) count / getLegalAnswers().size());
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        double rounded = bd.doubleValue();

        return rounded;
    }

    /**
     * Returns the DAO object for fill-in-the-blank questions
     *
     * @return the DAO object for fill-in-the-blank questions
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new Fill_InTheBlankQuestionDAO(DBConnection.getConnection());
    }

    /**
     * Returns a string representation of the object
     *
     * @return the string representation of the Fill_InTheBlankQuestion object
     */
    @Override
    public String toString() {
        return "Fill_InTheBlankQuestion {" +
                "id=" + getQuestionId() +
                ", question='" + getQuestion() + '\'' +
                ", legal answers=" + getLegalAnswers() +
                '}';
    }
}

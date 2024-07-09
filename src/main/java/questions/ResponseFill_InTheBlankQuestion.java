package questions;

import DAO.QuestionDAO;
import response.Response;

import java.sql.SQLException;
import java.util.List;

public abstract class ResponseFill_InTheBlankQuestion implements Question{
    private int id; // The unique ID for the question
    private final String question; // The question text
    protected final List<String> legalAnswers; // The list of legal answers

    /**
     * Constructor with question, legal answers, and id
     *
     * @param question     the question text
     * @param legalAnswers the list of legal answers
     * @param id           the question ID
     */
    protected ResponseFill_InTheBlankQuestion(String question, List<String> legalAnswers, int id) {
        this.id = id;
        this.question = question;
        this.legalAnswers = legalAnswers;
    }

    /**
     * Constructor with question and legal answers
     *
     * @param question     the question text
     * @param legalAnswers the list of legal answers
     */
    protected ResponseFill_InTheBlankQuestion(String question, List<String> legalAnswers) {
        this.question = question;
        this.legalAnswers = legalAnswers;
    }
    /**
     * Gets the question text
     *
     * @return the question text
     */
    @Override
    public long getQuestionId() { return id; }
    /**
     * Sets the question ID
     *
     * @param id the new question ID
     */
    public void setQuestionId(int id) { this.id = id; }
    /**
     * Gets the question text
     *
     * @return the question text
     */
    public String getQuestion() { return question; }
    /**
     * Gets the list of legal answers
     *
     * @return the list of legal answers
     */
    public List<String> getLegalAnswers() {return legalAnswers; }
    /**
     * Calculates the score for the given response
     *
     * @param response the response to the question
     * @return the score as a double value
     */
    public abstract double getScore(Response response);
    /**
     * Returns the DAO object for the specific question type
     *
     * @return the DAO object
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public abstract QuestionDAO getDao() throws SQLException, ClassNotFoundException;
}

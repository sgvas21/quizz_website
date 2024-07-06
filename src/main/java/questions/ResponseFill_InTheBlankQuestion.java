package questions;

import DAO.QuestionDAO;
import response.Response;

import java.sql.SQLException;
import java.util.List;

public abstract class ResponseFill_InTheBlankQuestion implements Question{
    private final int id;
    private final String question;
    protected final List<String> legalAnswers;

    protected ResponseFill_InTheBlankQuestion(String question, List<String> legalAnswers, int id) {
        this.id = id;
        this.question = question;
        this.legalAnswers = legalAnswers;
    }

    @Override
    public long getQuestionId() { return id; }
    public String getQuestion() { return question; }
    public List<String> getLegalAnswers() {return legalAnswers; }
    public abstract double getScore(Response response);
    public abstract QuestionDAO getDao() throws SQLException, ClassNotFoundException;
}

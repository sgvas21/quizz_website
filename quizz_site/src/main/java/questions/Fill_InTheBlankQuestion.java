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
    public Fill_InTheBlankQuestion(String question, List<String> legalAnswers, int id) {
        super(question, legalAnswers, id);
    }
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
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new Fill_InTheBlankQuestionDAO(DBConnection.getConnection());
    }
}

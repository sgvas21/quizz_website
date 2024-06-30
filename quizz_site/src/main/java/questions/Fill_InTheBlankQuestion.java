package questions;

import response.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Fill_InTheBlankQuestion implements Question {
    private int id;
    private String question;
    private List<String> legalAnswers;
    @Override
    public int getQuestionId() { return id; }

    @Override
    public void setQuestionId(int questionId) { this.id = questionId; }

    @Override
    public String getQuestion() { return question; }

    @Override
    public void setQuestion(String question) { this.question = question; }

    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        int count = 0;
        int idx = 0;
        while (iterator.hasNext()) {
            if (legalAnswers.get(idx).equals(iterator.next())) count++;
            idx++;
        }

        BigDecimal bd = new BigDecimal((double) count / legalAnswers.size());
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        double rounded = bd.doubleValue();

        return rounded;
    }

    public void setLegalAnswers(List<String> legalAnswers) { this.legalAnswers = legalAnswers; }

    public List<String> getLegalAnswers() { return legalAnswers; }

    @Override
    public String toString() {
        return "Fill_InTheBlankQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", legal answers='" + this.legalAnswers.toString() + '\'' +
                '}';
    }
}

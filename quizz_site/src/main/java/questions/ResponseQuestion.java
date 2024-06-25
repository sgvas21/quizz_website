package questions;

import response.Response;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ResponseQuestion implements Question {
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
        while(iterator.hasNext()) {
            if (legalAnswers.contains(iterator.next())) return 1;
        }
        return 0;
    }

    public void setLegalAnswers(List<String> legalAnswers) { this.legalAnswers = legalAnswers; }

    public List<String> getLegalAnswers() { return legalAnswers; }

    @Override
    public String toString() {
        return "ResponseQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", legal answers='" + this.legalAnswers.toString() + '\'' +
                '}';
    }

}

package questions;

import java.util.HashSet;
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

    public void setLegalAnswers(List<String> legalAnswers) { this.legalAnswers = legalAnswers; }

    public List<String> getLegalAnswers() { return legalAnswers; }

    @Override
    public String toString() {
        return "MultipleQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", legal answers='" + this.legalAnswers.toString() + '\'' +
                '}';
    }

}

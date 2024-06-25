package questions;

import response.Response;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MultipleChoiceQuestion implements Question {
    private int id;
    private String question;
    private List<String> incorrectAnswers;
    private String correctAnswer;

    public MultipleChoiceQuestion() {
    }

    public int getQuestionId() {
        return id;
    }

    public void setQuestionId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    //For each correct answer we get 1 point
    @Override
    public double getScore(Response response) {
        //TODO:
        return 0;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> answer) {
        this.incorrectAnswers = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "MultipleQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", correct answer='" + this.correctAnswer + '\'' +
                ", incorrect answers=" + this.incorrectAnswers.toString() +
                '}';
    }
}

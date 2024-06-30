package questions;

import response.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MultipleChoiceQuestion implements Question {
    private int id;
    private long quiz_id;
    private String question;
    private int numIncorrectAnswers;
    private List<String> incorrectAnswers;
    private String correctAnswer;

    public MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(int id, long quiz_id, String question, String correctAnswer, List<String> incorrectAnswers) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.numIncorrectAnswers = incorrectAnswers.size();
        this.quiz_id = quiz_id;
    }

    public int getQuestionId() {
        return id;
    }

    public void setQuestionId(int id) {
        this.id = id;
    }

    public long getQuizId() { return quiz_id; }

    public void setQuizId(long quiz_id) { this.quiz_id = quiz_id; }

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

    public List<String> getIncorrectAnswers() { return incorrectAnswers; }

    public void setIncorrectAnswers(List<String> answer) {
        this.incorrectAnswers = answer;
        this.numIncorrectAnswers = answer.size();
    }

    public int getNumIncorrectAnswers() { return numIncorrectAnswers; }

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
                ", quiz_id=" + this.quiz_id +
                ", question='" + this.question + '\'' +
                ", correct answer='" + this.correctAnswer + '\'' +
                ", numIncorrectAnswers=" + this.numIncorrectAnswers + '\'' +
                ", incorrect answers=" + this.incorrectAnswers.toString() +
                '}';
    }

    public static boolean equals(MultipleChoiceQuestion mcq1, MultipleChoiceQuestion mcq2) {
        return Objects.equals(mcq1.getQuestion(), mcq2.getQuestion()) &&
                mcq1.getQuizId() == mcq2.getQuizId() &&
                mcq1.getNumIncorrectAnswers() == mcq2.getNumIncorrectAnswers() &&
                mcq1.getIncorrectAnswers().equals(mcq2.getIncorrectAnswers());
    }
}

package questions;

import DAO.MultipleChoiceQuestionDAO;
import DAO.QuestionDAO;
import database.DBConnection;
import response.Response;

import java.sql.SQLException;
import java.util.*;

public class MultipleChoiceQuestion implements Question {
    private long id;
    private String question;
    private List<String> incorrectAnswers;
    private String correctAnswer;

    public MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(String question, String correctAnswer, List<String> incorrectAnswers) {
        if(incorrectAnswers.isEmpty()) {
            throw new RuntimeException("Incorrect answers are not given");
        }

        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public MultipleChoiceQuestion(long id, String question, String correctAnswer, List<String> incorrectAnswers) {
        this(question, correctAnswer, incorrectAnswers);
        this.id = id;
    }

    /*Getters/Setters*/

    //Question ID
    public long getQuestionId() { return id; }

    public void setQuestionId(long id) { this.id = id; }

    //Question
    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    //Incorrect Answers
    public List<String> getIncorrectAnswers() { return incorrectAnswers; }

    public void setIncorrectAnswers(List<String> incorrectAnswers) { this.incorrectAnswers = incorrectAnswers; }

    //Correct Answer
    public String getCorrectAnswer() { return correctAnswer; }

    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    //All Answers in random order
    public List<String> getAllAnswers() {
        List<String> answers = new ArrayList<>();
        answers.add(correctAnswer);
        answers.addAll(incorrectAnswers);

        Collections.shuffle(answers);
        return answers;
    }

    //For each correct answer we get 1 point
    @Override
    public double getScore(Response response) {
        Iterator<String> allAnswers = response.getAllAnswers();
        while(allAnswers.hasNext()) {
            if(correctAnswer.equals(allAnswers.next())) {
                return 1.0;
            }
        }
        return 0.0;
    }
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new MultipleChoiceQuestionDAO(DBConnection.getConnection());
    }
    @Override
    public String toString() {
        return "MultipleQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", correct answers='" + this.correctAnswer + '\'' +
                ", incorrect answers=" + this.incorrectAnswers.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof MultipleChoiceQuestion)) return false;

        MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;
        return this.id == that.id &&
                Objects.equals(this.question, that.question) &&
                Objects.equals(this.correctAnswer, that.correctAnswer) &&
                Objects.deepEquals(this.incorrectAnswers, that.incorrectAnswers);

    }
}

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

    /**
     * Retrieves all answers (both correct and incorrect) in random order.
     *
     * @return a list containing all answers in random order
     */
    public List<String> getAllAnswers() {
        List<String> answers = new ArrayList<>();
        answers.add(correctAnswer);
        answers.addAll(incorrectAnswers);

        Collections.shuffle(answers);
        return answers;
    }

    /**
     * Calculates the score based on the provided response.
     * Returns 1.0 if the correct answer matches any answer in the response, otherwise returns 0.0.
     *
     * @param response the response object containing user-provided answers
     * @return the score as a double value (1.0 for correct, 0.0 for incorrect)
     */
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

    /**
     * Returns a DAO object specific to handling multiple choice questions.
     *
     * @return the DAO object for handling multiple choice questions
     * @throws SQLException            if a database access error occurs
     * @throws ClassNotFoundException  if the database driver class is not found
     */
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new MultipleChoiceQuestionDAO(DBConnection.getConnection());
    }

    /**
     * Returns a string representation of the MultipleChoiceQuestion object.
     *
     * @return a string representation of the MultipleChoiceQuestion object
     */
    @Override
    public String toString() {
        return "MultipleQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", correct answers='" + this.correctAnswer + '\'' +
                ", incorrect answers=" + this.incorrectAnswers.toString() +
                '}';
    }

    /**
     * Checks if two MultipleChoiceQuestion objects are equal.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
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

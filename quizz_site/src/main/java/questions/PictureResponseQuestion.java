package questions;

import DAO.PictureResponseQuestionDAO;
import DAO.QuestionDAO;
import database.DBConnection;
import response.Response;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class PictureResponseQuestion implements Question{
    private int id;
    private String question;
    private String picURL;
    private List<String> legalAnswers;

    public PictureResponseQuestion(String question, List<String> legalAnswers, String picURL) {
        this.question = question;
        this.legalAnswers = legalAnswers;
        this.picURL = picURL;
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

    public String getPicURL(){
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public List<String> getLegalAnswers() {
        return legalAnswers;
    }

    public void setCorrectAnswer(List<String> legalAnswers) {
        this.legalAnswers = legalAnswers;
    }

    //For each correct answer we get 1 point
    @Override
    public double getScore(Response response) {
        Iterator<String> allAnswers = response.getAllAnswers();
        double count = 0;
        while (allAnswers.hasNext()) {
            allAnswers.next();
            count += 1.0;
        }
        return count;
    }
    @Override
    public QuestionDAO getDao() throws SQLException, ClassNotFoundException {
        return new PictureResponseQuestionDAO(DBConnection.getConnection());
    }

    @Override
    public String toString() {
        return "PictureResponseQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", picURL='" + this.picURL + '\'' +
                ", legal answers=" + this.legalAnswers +
                '}';
    }
}

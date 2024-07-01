package questions;

import response.Response;

public interface Question {
    //int getQuestionId();
    //void setQuestionId(int questionId);
    String getQuestion();
    //void setQuestion(String question);
    double getScore(Response response);
}

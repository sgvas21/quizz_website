package questions;

public class PictureResponseQuestion implements Question{
    private int id;
    private String question;
    private String picURL;
    private String correctAnswer;

    public PictureResponseQuestion() {
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "PictureResponseQuestion {" +
                "id=" + this.id +
                ", question='" + this.question + '\'' +
                ", picURL='" + this.picURL + '\'' +
                ", correct answers=" + this.correctAnswer +
                '}';
    }

}

package quizz;

import questions.Question;
import user.User;
import java.util.List;

public abstract class quizz {
    private long id;
    private String name;
    private User author;
    protected List<Question> questions;
    private List<quizzAttempt> history;

    public quizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.questions = questions;
        this.history = history;
    }

    public quizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        this.name = name;
        this.author = author;
        this.questions = questions;
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author){
        this.author = author;
    }


    public List<quizzAttempt> getHistory() { return history; }

    public abstract List<Question> getQuestions();
}
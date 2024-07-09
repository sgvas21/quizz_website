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

    // Constructor with id, initializing all fields
    public quizz(long id,  String name, User author, List<Question> questions, List<quizzAttempt> history) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.questions = questions;
        this.history = history;
    }

    // Constructor without id, useful for creating new quizzes before saving to database
    public quizz(String name, User author, List<Question> questions, List<quizzAttempt> history) {
        this.name = name;
        this.author = author;
        this.questions = questions;
        this.history = history;
    }

    // Getter for quiz name
    public String getName() {
        return name;
    }

    // Setter for quiz name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for quiz id
    public long getId() {
        return id;
    }

    // Setter for quiz id
    public void setId(long id) { this.id = id; }

    // Getter for quiz author
    public User getAuthor() {
        return author;
    }

    // Setter for quiz author
    public void setAuthor(User author){
        this.author = author;
    }

    // Getter for quiz history
    public List<quizzAttempt> getHistory() { return history; }

    // Abstract method to get the list of questions in the quiz
    public abstract List<Question> getQuestions();
}

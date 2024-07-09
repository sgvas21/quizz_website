package quizz;

import java.sql.Timestamp;

public class quizzAttempt {
    private long id; // Unique identifier for the attempt
    private final long userId; // User ID of the participant
    private double score; // Score achieved in the quiz attempt
    private final Timestamp timestamp; // Timestamp when the attempt was made

    // Constructor with id, initializing all fields
    public quizzAttempt(long id, long userId, double score, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    // Constructor without id, useful for creating new attempts before saving to database
    public quizzAttempt(long userId, double score, Timestamp timestamp) {
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    // Getter for attempt id
    public long getId() {
        return id;
    }

    // Setter for attempt id
    public void setId(long id) {
        this.id = id;
    }

    // Getter for user id
    public long getUserId() {
        return userId;
    }

    // Getter for score
    public double getScore() {
        return score;
    }

    // Setter for score
    public void setScore(double score) {
        this.score = score;
    }

    // Getter for timestamp
    public Timestamp getTimestamp() {
        return timestamp;
    }
}

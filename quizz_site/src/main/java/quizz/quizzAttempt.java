package quizz;

import java.sql.Timestamp;

public class quizzAttempt {
    private long id;
    private final long userId;
    private double score;
    private final Timestamp timestamp;

    public quizzAttempt(long id, long userId, double score, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public quizzAttempt(long userId, double score, Timestamp timestamp) {
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
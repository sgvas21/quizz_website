package user;

import java.sql.Timestamp;

public class UserAttemptResult {
    // Attributes
    private long id;             // Unique identifier for the attempt result
    private long quizId;         // ID of the quiz attempted
    private long userId;         // ID of the user who attempted the quiz
    private double score;        // Score achieved by the user
    private Timestamp timestamp; // Timestamp when the attempt was made

    // Constructors
    public UserAttemptResult(long quizId, long userId, double score, Timestamp timestamp) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public UserAttemptResult(long id, long quizId, long userId, double score, Timestamp timestamp) {
        this(quizId, userId, score, timestamp);
        this.id = id;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuizId() {
        return quizId;
    }

    public long getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    public Timestamp getTimeSpent() {
        return timestamp;
    }

    // Override Methods

    @Override
    public String toString() {
        return "UserAttemptResult {" +
                "id: " + this.id +
                ", quizId: " + this.quizId +
                ", userId: " + this.userId +
                ", score: " + (this.score * 1.00) + // Format score as double
                ", time Spent: " + timestamp.getTime() + // Get time in milliseconds
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAttemptResult)) return false;
        UserAttemptResult that = (UserAttemptResult) o;
        return this.id == that.id;
    }
}

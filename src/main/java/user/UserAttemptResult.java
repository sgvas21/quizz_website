package user;

import java.sql.Timestamp;

public class UserAttemptResult {
    private long id;
    private long quizId;
    private long userId;
    private double score;
    Timestamp timestamp;

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

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

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

    @Override
    public String toString() {
        return "UserAttemptResult {" +
                "id: " + this.id +
                ", quizId: " + this.quizId +
                ", userId: " + this.userId +
                ", score: " + (this.score * 1.00) +
                ", time Spent: " + timestamp.getTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAttemptResult that)) return false;

        return this.id == that.id;

    }
}

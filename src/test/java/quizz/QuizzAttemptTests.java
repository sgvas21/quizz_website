package quizz;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizzAttemptTests {

    @Test
    public void testQuizzAttemptWithId() {
        long id = 1;
        long userId = 1001;
        double score = 85.5;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        quizzAttempt attempt = new quizzAttempt(id, userId, score, timestamp);

        // Assertions to verify the values
        assertEquals(id, attempt.getId(), "Id should match");
        assertEquals(userId, attempt.getUserId(), "User Id should match");
        assertEquals(score, attempt.getScore(), 0.01, "Score should match with tolerance");
        assertEquals(timestamp, attempt.getTimestamp(), "Timestamp should match");
    }

    @Test
    public void testQuizzAttemptWithoutId() {
        long userId = 1001;
        double score = 92.0;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        quizzAttempt attempt = new quizzAttempt(userId, score, timestamp);

        // Assertions to verify the values
        assertEquals(userId, attempt.getUserId(), "User Id should match");
        assertEquals(score, attempt.getScore(), 0.01, "Score should match with tolerance");
        assertEquals(timestamp, attempt.getTimestamp(), "Timestamp should match");
    }
}

package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAttemptResultTest {
    private final long SAMPLE_ID = -1;
    private final long SAMPLE_QUIZ_ID = 1;
    private final long SAMPLE_USER_ID = 2;
    private final double SAMPLE_SCORE = -3.0;
    private final long SAMPLE_TIME_RESULT = System.currentTimeMillis();
    private final Timestamp SAMPLE_TIMESTAMP = new Timestamp(SAMPLE_TIME_RESULT);

    private UserAttemptResult userAttemptResult, userAttemptResultWithId;

    @BeforeEach
    public void setUp() {
        userAttemptResult = new UserAttemptResult(SAMPLE_QUIZ_ID, SAMPLE_USER_ID, SAMPLE_SCORE, SAMPLE_TIMESTAMP);
        userAttemptResultWithId = new UserAttemptResult(SAMPLE_ID, SAMPLE_QUIZ_ID, SAMPLE_USER_ID, SAMPLE_SCORE, SAMPLE_TIMESTAMP);
    }

    @Test
    public void test_Constructor() {
        assertEquals(SAMPLE_QUIZ_ID, userAttemptResult.getQuizId());
        assertEquals(SAMPLE_USER_ID, userAttemptResult.getUserId());
        assertEquals(SAMPLE_SCORE, userAttemptResult.getScore());
        assertEquals(SAMPLE_TIMESTAMP, userAttemptResult.getTimeSpent());
        assertEquals(SAMPLE_TIME_RESULT, userAttemptResult.getTimeSpent().getTime());
    }

    @Test
    public void test_ConstructorWithId() {
        assertEquals(SAMPLE_ID, userAttemptResultWithId.getId());
        assertEquals(SAMPLE_QUIZ_ID, userAttemptResultWithId.getQuizId());
        assertEquals(SAMPLE_USER_ID, userAttemptResultWithId.getUserId());
        assertEquals(SAMPLE_SCORE, userAttemptResultWithId.getScore());
        assertEquals(SAMPLE_TIMESTAMP, userAttemptResultWithId.getTimeSpent());
        assertEquals(SAMPLE_TIME_RESULT, userAttemptResultWithId.getTimeSpent().getTime());
    }

    @Test
    public void test_Getters_Setters() {
        assertNotEquals(userAttemptResult.getId(), userAttemptResultWithId.getId());
        assertNotEquals(userAttemptResult, userAttemptResultWithId);

        userAttemptResult.setId(SAMPLE_ID);
        assertEquals(SAMPLE_ID, userAttemptResult.getId());

        assertEquals(userAttemptResult.getId(), userAttemptResultWithId.getId());
    }
}

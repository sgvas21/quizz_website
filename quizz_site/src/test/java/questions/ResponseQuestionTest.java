package questions;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import response.AnswerResponse;
import response.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseQuestionTest {
    private static ResponseQuestion rq;
    @BeforeAll
    public static void setUp() {
        List<String> legalAnswers = new ArrayList<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");
        legalAnswers.add("ans4");

        rq = new ResponseQuestion("question1", legalAnswers, -1);

        assertNotNull(rq.getQuestion());
        assertNotNull(rq.getLegalAnswers());
    }

    @Test
    public void Test_GetScore1() {
        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add("ans1");
        Response response = new AnswerResponse(responseAnswer);

        assertEquals(1, rq.getScore(response));
    }

    @Test
    public void Test_GetScore2() {
        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add("ans5");
        Response response = new AnswerResponse(responseAnswer);

        assertEquals(0, rq.getScore(response));
    }

    @Test
    public void Test_GetScore3() {
        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add(null);
        Response response = new AnswerResponse(responseAnswer);

        assertEquals(0, rq.getScore(response));
    }
}
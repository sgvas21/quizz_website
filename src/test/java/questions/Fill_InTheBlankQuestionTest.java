package questions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import response.AnswerResponse;
import response.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Fill_InTheBlankQuestionTest {
    private static Fill_InTheBlankQuestion fq;
    @Test
    public void Test_FillInTheBlankQuestion0() {
        List<String> legalAnswers = new ArrayList<>();
        legalAnswers.add("ans1");

        fq = new Fill_InTheBlankQuestion("bla bla ___ bla bla bla", legalAnswers, -1);

        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add("ans1");

        Response response = new AnswerResponse(responseAnswer);

        assertEquals(1, fq.getScore(response));

    }
    @Test
    public void Test_FillInTheBlankQuestion1() {
        List<String> legalAnswers = new ArrayList<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");

        fq = new Fill_InTheBlankQuestion("bla bla ___ bla bla bla ___ bla", legalAnswers, 0);

        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add("ans1");
        responseAnswer.add("ans2");

        Response response = new AnswerResponse(responseAnswer);

        assertEquals(1, fq.getScore(response));
    }

    @Test
    public void Test_FillInTheBlankQuestion2() {
        List<String> legalAnswers = new ArrayList<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");

        fq = new Fill_InTheBlankQuestion("bla bla ___ bla bla bla ___ bla ___ bla bla bla", legalAnswers, 1);

        List<String> responseAnswer = new ArrayList<>();
        responseAnswer.add("ans2");

        Response response = new AnswerResponse(responseAnswer);

        assertEquals(0, fq.getScore(response));
    }

    @Test
    public void Test_FillInTheBlankQuestion3() {
        List<String> legalAnswers = new ArrayList<>();
        legalAnswers.add("ans1");
        legalAnswers.add("ans2");
        legalAnswers.add("ans3");
        legalAnswers.add("ans4");
        legalAnswers.add("ans5");
        legalAnswers.add("ans6");

        fq = new Fill_InTheBlankQuestion("bla bla ___ bla bla ___ " +
                "bla bla bla ___ bla bla bla ___ " +
                "bla bla bla bla ___ bla ___ bla", legalAnswers, 2);

        List<String> responseAnswer1 = new ArrayList<>();
        responseAnswer1.add("ans1");
        Response response1 = new AnswerResponse(responseAnswer1);
        assertEquals(0.17, fq.getScore(response1));

        List<String> responseAnswer2 = new ArrayList<>();
        responseAnswer2.add("ans1");
        responseAnswer2.add("ans3");
        responseAnswer2.add("ans2");
        Response response2 = new AnswerResponse(responseAnswer2);
        assertEquals(0.17, fq.getScore(response2));

        List<String> responseAnswer3 = new ArrayList<>();
        responseAnswer3.add("ans1");
        responseAnswer3.add("ans2");
        responseAnswer3.add("ans3");
        responseAnswer3.add("ans4");
        Response response3 = new AnswerResponse(responseAnswer3);
        assertEquals(0.67, fq.getScore(response3));

        List<String> responseAnswer4 = new ArrayList<>();
        responseAnswer4.add("ans1");
        responseAnswer4.add("ans2");
        responseAnswer4.add("ans3");
        responseAnswer4.add("incorrect");
        responseAnswer4.add("ans5");
        responseAnswer4.add("ans6");
        Response response4 = new AnswerResponse(responseAnswer4);
        assertEquals(0.83, fq.getScore(response4));

        List<String> responseAnswer5 = new ArrayList<>();
        responseAnswer5.add("ans1");
        responseAnswer5.add("ans2");
        responseAnswer5.add("ans3");
        responseAnswer5.add("ans4");
        responseAnswer5.add("ans5");
        responseAnswer5.add("ans6");
        Response response5 = new AnswerResponse(responseAnswer5);
        assertEquals(1, fq.getScore(response5));

        List<String> responseAnswer6 = new ArrayList<>();
        responseAnswer6.add("ans2");
        responseAnswer6.add("ans1");
        responseAnswer6.add("ans4");
        responseAnswer6.add("ans3");
        responseAnswer6.add("ans6");
        responseAnswer6.add("ans5");
        Response response6 = new AnswerResponse(responseAnswer6);
        assertEquals(0, fq.getScore(response6));

        List<String> responseAnswer7 = new ArrayList<>();
        responseAnswer7.add(null);
        responseAnswer7.add(null);
        responseAnswer7.add("ans3");
        responseAnswer7.add(null);
        responseAnswer7.add(null);
        responseAnswer7.add(null);
        Response response7 = new AnswerResponse(responseAnswer7);
        assertEquals(0.17, fq.getScore(response7));
    }
}
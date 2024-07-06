package questions;

import org.junit.jupiter.api.Test;
import response.AnswerResponse;
import response.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PictureResponseQuestionTest {
    @Test
    public void PictureResponseQuestions_bean() {
        int id = -1;

        String question = "What is next: 1 2 4 8 16 ?";
        String picUrl = "https://www.google.com/";
        List<String> correctAnswers = Arrays.asList("32");

        PictureResponseQuestion pcr = new PictureResponseQuestion(question, correctAnswers, picUrl);

        // Verify initial state
        assertEquals(0, pcr.getQuestionId());  // ID should be 0 initially
        assertEquals(question, pcr.getQuestion());
        assertEquals(picUrl, pcr.getPicURL());
        assertEquals(correctAnswers, pcr.getLegalAnswers());

        // Set properties
        pcr.setQuestionId(id);

        // Verify the properties have been set correctly
        assertEquals(id, pcr.getQuestionId());
        assertEquals(question, pcr.getQuestion());
        assertEquals(picUrl, pcr.getPicURL());
        assertEquals(correctAnswers, pcr.getLegalAnswers());

        Response response = new AnswerResponse(Arrays.asList("32")); // Assuming Response class has a constructor that accepts a list of answers
        double score = pcr.getScore(response);
        assertEquals(1.0, score, 0.0);
    }
}
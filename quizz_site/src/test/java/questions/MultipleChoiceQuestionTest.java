package questions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import response.AnswerResponse;
import response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceQuestionTest {
    static int sampleId;
    static String sampleQuestion;
    static String sampleCorrectAnswer;
    static List<String> sampleIncorrectAnswers;

    @BeforeAll
    public static void setUp() {
        sampleId = -1;
        sampleQuestion = "What is next: 1 2 4 8 16 ?";
        sampleCorrectAnswer = "31";
        sampleIncorrectAnswers = List.of(new String[]{"64", "0", "16"});
    }

    @Test
    public void test1_GettersAndSetters() {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();

        assertNull(mcq.getQuestion());
        assertNull(mcq.getCorrectAnswer());
        assertNull(mcq.getIncorrectAnswers());

        mcq.setQuestionId(sampleId);
        mcq.setQuestion(sampleQuestion);
        mcq.setCorrectAnswer(sampleCorrectAnswer);
        mcq.setIncorrectAnswers(sampleIncorrectAnswers);

        assertEquals(sampleId, mcq.getQuestionId());
        assertEquals(sampleQuestion, mcq.getQuestion());
        assertEquals(sampleCorrectAnswer, mcq.getCorrectAnswer());
        assertEquals(sampleIncorrectAnswers, mcq.getIncorrectAnswers());

        System.out.println("id: " + mcq.getQuestionId());
        System.out.println("question: " + mcq.getQuestion());
        System.out.println("correct answer: " + mcq.getCorrectAnswer());
        System.out.println("incorrect answers: " + mcq.getIncorrectAnswers());
        System.out.println(mcq);
    }

    @Test
    public void test2_InitializingWithConstructor() {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(sampleId, sampleQuestion, sampleCorrectAnswer, sampleIncorrectAnswers);

        assertEquals(sampleId, mcq.getQuestionId());
        assertEquals(sampleQuestion, mcq.getQuestion());
        assertEquals(sampleCorrectAnswer, mcq.getCorrectAnswer());
        assertEquals(sampleIncorrectAnswers, mcq.getIncorrectAnswers());
    }

    @Test
    public void test3_getScore_correct() {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(sampleId, sampleQuestion, sampleCorrectAnswer, sampleIncorrectAnswers);
        Response answerResponse = new AnswerResponse(List.of(sampleCorrectAnswer));

        assertEquals(1, mcq.getScore(answerResponse));
    }

    @Test
    public void test4_getScore_incorrect() {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(sampleId, sampleQuestion, sampleCorrectAnswer, sampleIncorrectAnswers);
        Response answerResponse = new AnswerResponse(sampleIncorrectAnswers);

        assertEquals(0, mcq.getScore(answerResponse));
    }
}

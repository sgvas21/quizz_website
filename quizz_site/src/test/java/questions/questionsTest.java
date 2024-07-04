package questions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class questionsTest {

    @Test
    public void testMultipleChoice_bean() {
        int id = -1;

        String question = "What is next: 1 2 4 8 16 ?";
        List<String> incorrectAnswers = new ArrayList<>();
        incorrectAnswers.add("64");
        incorrectAnswers.add("0");
        incorrectAnswers.add("16");
        String correctAnswer = "32";

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();

        assertNull(mcq.getQuestion());
        assertNull(mcq.getCorrectAnswer());
        assertNull(mcq.getIncorrectAnswers());

        mcq.setQuestionId(id);
        mcq.setQuestion(question);
        mcq.setCorrectAnswer(correctAnswer);
        mcq.setIncorrectAnswers(incorrectAnswers);

        assertNotNull(mcq.getQuestion());
        assertNotNull(mcq.getCorrectAnswer());
        assertNotNull(mcq.getIncorrectAnswers());

        System.out.println("id: " + mcq.getQuestionId());
        System.out.println("question: " + mcq.getQuestion());
        System.out.println("correct answer: " + mcq.getCorrectAnswer());
        System.out.println("incorrect answers: " + mcq.getIncorrectAnswers());
        System.out.println(mcq);

        assertEquals(id, mcq.getQuestionId());
        assertEquals(question, mcq.getQuestion());
        assertEquals(correctAnswer, mcq.getCorrectAnswer());
        assertEquals(incorrectAnswers, mcq.getIncorrectAnswers());

    }
    @Test
    public void PictureResponseQuestions_bean() {
        int id = -1;

        String question = "What is next: 1 2 4 8 16 ?";
        String picUrl = "https://www.google.com/";
        String correctAnswer = "32";

        PictureResponseQuestion pcr = new PictureResponseQuestion();

        assertNull(pcr.getQuestion());
        assertNull(pcr.getCorrectAnswer());
        assertNull(pcr.getPicURL());

        pcr.setQuestionId(id);
        pcr.setQuestion(question);
        pcr.setPicURL(picUrl);
        pcr.setCorrectAnswer(correctAnswer);    

        assertNotNull(pcr.getQuestion());
        assertNotNull(pcr.getCorrectAnswer());
        assertNotNull(pcr.getPicURL());

        System.out.println("id: " + pcr.getQuestionId());
        System.out.println("question: " + pcr.getQuestion());
        System.out.println("picURL: " + pcr.getPicURL());
        System.out.println("correct answer: " + pcr.getCorrectAnswer());
        System.out.println(pcr);
    }
}
package questions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class test {

    @Test
    public void testMultipleChoice_bean() {
        int id = -1;

        String question = "What is next: 1 2 4 8 16 ?";
        List<String> incorrectAnswers = new ArrayList<>();
        incorrectAnswers.add("64");
        incorrectAnswers.add("0");
        incorrectAnswers.add("16");
        String correctAnswer = "31";

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
    }
}

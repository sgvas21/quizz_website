package quizz;

import org.junit.jupiter.api.Test;
import user.User;
import questions.Question;
import questions.PictureResponseQuestion;
import questions.Fill_InTheBlankQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class randomQuizzTests {

    @Test
    public void testRandomQuizz() {
        // Create sample questions
        List<Question> questions = Arrays.asList(
                new PictureResponseQuestion("Question 1", Arrays.asList("Answer 1"), "pic1.jpg"),
                new Fill_InTheBlankQuestion("Question 2", Arrays.asList("Answer 2", "Answer 3")),
                new PictureResponseQuestion("Question 3", Arrays.asList("Answer 4", "Answer 5"), "pic2.jpg")
        );

        // Create a randomQuizz instance for testing
        User author = new User(1, "testuser", "password", "John", "Doe", false);
        randomQuizz quizz = new randomQuizz(1, "Test Quizz", author, new ArrayList<>(questions), new ArrayList<>());

        // Test the getQuestions method
        List<Question> quizzQuestions = quizz.getQuestions();

        // Assertions to validate the behavior of getQuestions method
        assertEquals(questions.size(), quizzQuestions.size(), "Number of questions should match");

        System.out.println(quizzQuestions);

        // You can add more assertions to compare individual questions if needed
        assertTrue(quizzQuestions.contains(questions.get(0)));
        assertTrue(quizzQuestions.contains(questions.get(1)));
        assertTrue(quizzQuestions.contains(questions.get(2)));
    }
}

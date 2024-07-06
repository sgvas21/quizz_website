package DAO;

import database.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import questions.MultipleChoiceQuestion;
import questions.PictureResponseQuestion;
import questions.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceQuestionDAOTest {
    private static final long SAMPLE_MCQ_QUIZ_ID = -1;
    private static final long MULTISAMPLE_MCQ_QUIZ_ID = -2;

    static Connection connection;

    private static MultipleChoiceQuestion sampleMCQ;
    private static MultipleChoiceQuestion mcq1, mcq2, mcq3;

    @BeforeAll
    public static void initialize() throws ClassNotFoundException, SQLException {
        connection = DBConnection.getConnection();

        sampleMCQ = new MultipleChoiceQuestion();
        sampleMCQ.setQuestion("What is your name?");
        sampleMCQ.setCorrectAnswer("my name is ...");
        sampleMCQ.setIncorrectAnswers(List.of(new String[]{"name", "zh", "unknown"}));

        mcq1 = new MultipleChoiceQuestion("question1", "thisAnswer1", List.of(new String[]{"notAnswer1"}));
        mcq2 = new MultipleChoiceQuestion("question2", "thisAnswer2", List.of(new String[]{"notAnswer1", "notAnswer2"}));
        mcq3 = new MultipleChoiceQuestion("question3", "thisAnswer3", List.of(new String[]{"notAnswer1", "notAnswer2", "notAnswer3"}));
    }

    @Test
    public void testAddQuestion() throws SQLException, ClassNotFoundException {
        MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO(connection);
        mcqDAO.addQuestion(sampleMCQ, SAMPLE_MCQ_QUIZ_ID);

        String query = "SELECT * FROM MultipleChoiceQuestions;";
        PreparedStatement statement1 = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        assertTrue(result1.next());

        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");

        assertEquals(sampleMCQ.getQuestion(), text);

        List<String> incorrectAnswers = new ArrayList<>();
        String correctAnswer = "";
        PreparedStatement statement_1 = connection.prepareStatement(
                "SELECT * FROM MultipleChoiceQuestionsAnswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) {
            if(result_1.getBoolean("is_correct_answer"))
                correctAnswer = result_1.getString("answer");
            else
                incorrectAnswers.add(result_1.getString("answer"));
        }

        assertEquals(sampleMCQ.getCorrectAnswer(), correctAnswer);
        assertEquals(sampleMCQ.getIncorrectAnswers(), incorrectAnswers);

        //Delete From Table
        deleteQuestionFromDB(questionId1);
    }

    private void deleteQuestionFromDB(long questionId) throws SQLException, ClassNotFoundException {
        String sqlDeleteQuestion = "DELETE FROM MultipleChoiceQuestions WHERE id=?;";
        PreparedStatement statement2 = connection.prepareStatement(sqlDeleteQuestion);
        statement2.setLong(1, questionId);
        statement2.execute();
    }

    @Test
    public void testGetQuestions() throws SQLException, ClassNotFoundException {
        MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO(connection);
        mcqDAO.addQuestion(mcq1, MULTISAMPLE_MCQ_QUIZ_ID);
        mcqDAO.addQuestion(mcq2, MULTISAMPLE_MCQ_QUIZ_ID);
        mcqDAO.addQuestion(mcq3, MULTISAMPLE_MCQ_QUIZ_ID);

        List<Question> expectedQuestions = List.of(mcq1, mcq2, mcq3);
        List<Question> actualQuestions = mcqDAO.getQuestions(MULTISAMPLE_MCQ_QUIZ_ID);

        assertEquals(expectedQuestions, actualQuestions);

        deleteQuestionFromDB(mcq1.getQuestionId());
        deleteQuestionFromDB(mcq2.getQuestionId());
        deleteQuestionFromDB(mcq3.getQuestionId());
    }
}

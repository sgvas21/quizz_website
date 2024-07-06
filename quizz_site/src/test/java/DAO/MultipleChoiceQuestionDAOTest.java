package DAO;

import database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import questions.MultipleChoiceQuestion;
import questions.PictureResponseQuestion;
import questions.Question;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceQuestionDAOTest {
    private static final long SAMPLE_MCQ_QUIZ_ID = 1;
    private static final int MULTISAMPLE_MCQ_QUIZ_ID = 1;

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

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users(username, hashedPassword, isAdmin, firstName, lastName) " +
                        "VALUES ('user1', 'psw', false, 'nm', 'ln');");
        PreparedStatement ps2 = connection.prepareStatement(
                "INSERT INTO quizzes(author, quizName, type) VALUES (1, 'quiz1', 'default');");

        ps.execute();
        ps2.execute();
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException {
        DBConnection.resetTables();
    }

    @Test
    public void testAddQuestion() throws SQLException, ClassNotFoundException {
        MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO(connection);
        mcqDAO.addQuestion(sampleMCQ, SAMPLE_MCQ_QUIZ_ID);

        MultipleChoiceQuestion returnedMCQFromDB = new MultipleChoiceQuestion();

        PreparedStatement statement1 = connection.prepareStatement(
                "SELECT * FROM MultipleChoiceQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");

        assertEquals(sampleMCQ.getQuestion(), text);
        returnedMCQFromDB.setQuestionId(questionId1);
        returnedMCQFromDB.setQuestion(text);

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
        returnedMCQFromDB.setCorrectAnswer(correctAnswer);
        returnedMCQFromDB.setIncorrectAnswers(incorrectAnswers);

        assertEquals(sampleMCQ, returnedMCQFromDB);

        //Delete From Table
        deleteQuestionFromDB(questionId1);
    }

    private void deleteQuestionFromDB(long questionId) throws SQLException, ClassNotFoundException {
        String sqlDeleteAnswers = "DELETE FROM MultipleChoiceQuestionsAnswers WHERE questionId=?;";
        PreparedStatement statement1 = connection.prepareStatement(sqlDeleteAnswers);
        statement1.setLong(1, questionId);
        statement1.execute();

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

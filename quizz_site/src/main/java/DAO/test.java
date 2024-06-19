package DAO;

import database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.MultipleChoiceQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class test {
    static Connection connection;
    static PreparedStatement statement;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DBConnection.getConnection();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        statement.close();
        connection.close();
    }

    @Test
    public void testDBConnectionAcquired() throws SQLException, ClassNotFoundException {
        assertNotNull(connection);
    }

    @Test
    public void testMultipleChoiceQuestion_addQuestion() throws SQLException, ClassNotFoundException {
        statement = connection.prepareStatement("select count(*) from MultipleChoiceQuestions");
        ResultSet initResultSet = statement.executeQuery();
        initResultSet.next();
        int initialRows = initResultSet.getInt(1);

        MultipleChoiceQuestionDAO questionDAO = new MultipleChoiceQuestionDAO();
        questionDAO.addQuestion(new MultipleChoiceQuestion());

        ResultSet finalResultSet = statement.executeQuery();
        finalResultSet.next();
        int finalRows = finalResultSet.getInt(1);

        assertEquals(initialRows + 1, finalRows);
    }

    @Test
    public void testMultipleChoiceQuestion_getQuestion() throws SQLException, ClassNotFoundException {
        statement = connection.prepareStatement("select * from MultipleChoiceQuestions LEFT JOIN databaza.MultipleChoiceQuestionsWrongAnswers MCQWA on MultipleChoiceQuestions.id = MCQWA.questionId");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("question"));
            System.out.println(resultSet.getString("correctAnswer"));

            MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO();
            MultipleChoiceQuestion mcq = mcqDAO.getQuestion();

            assertEquals(resultSet.getInt("id"), mcq.getQuestionId());
            assertEquals(resultSet.getString("question"), mcq.getQuestion());
            assertEquals(resultSet.getString("correctAnswer"), mcq.getCorrectAnswer());
        }
    }
}

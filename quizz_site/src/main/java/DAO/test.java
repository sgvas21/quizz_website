package DAO;

import database.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.MultipleChoiceQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class test {
    private static final long SAMPLE_MCQ_QUIZ_ID = -1;

    static Connection connection;
    static PreparedStatement statement;

    private MultipleChoiceQuestion sampleMCQ;

    @BeforeAll
    public static void initialize() throws ClassNotFoundException, SQLException {
        connection = DBConnection.getConnection();
    }

    @BeforeEach
    public void setUp() throws Exception {
        sampleMCQ = new MultipleChoiceQuestion();
        sampleMCQ.setQuizId(SAMPLE_MCQ_QUIZ_ID);
        sampleMCQ.setQuestion("What is your name?");
        sampleMCQ.setCorrectAnswer("my name is ...");
        sampleMCQ.setIncorrectAnswers(List.of(new String[]{"name", "zh", "unknown"}));
    }

//    @AfterAll
//    public static void tearDown() throws SQLException {
//        statement.close();
//        connection.close();
//    }

    @Test
    public void testMultipleChoiceQuestion_addQuestion() throws SQLException, ClassNotFoundException {
        MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO(DBConnection.getURL(), DBConnection.getUsername(), DBConnection.getPassword());
        mcqDAO.addQuestion(sampleMCQ, SAMPLE_MCQ_QUIZ_ID);

        statement = connection.prepareStatement("select * from MultipleChoiceQuestions where question = ? and quiz_id = ?");
        statement.setString(1, sampleMCQ.getQuestion());
        statement.setLong(2, SAMPLE_MCQ_QUIZ_ID);

        ResultSet rs = statement.executeQuery();
        assertTrue(rs.next());
        assertEquals(sampleMCQ.getQuestion(), rs.getString("question"));

        deleteQuestion(sampleMCQ);
    }

    private void deleteQuestion(MultipleChoiceQuestion sampleMCQ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from MultipleChoiceQuestions where question = ?");
        statement.setString(1, sampleMCQ.getQuestion());
        statement.executeUpdate();
    }

    @Test
    public void testMultipleChoiceQuestion_getQuestions() throws SQLException, ClassNotFoundException {
        MultipleChoiceQuestionDAO mcqDAO = new MultipleChoiceQuestionDAO(DBConnection.getURL(), DBConnection.getUsername(), DBConnection.getPassword());
        mcqDAO.addQuestion(sampleMCQ, SAMPLE_MCQ_QUIZ_ID);

        statement = connection.prepareStatement("select * from MultipleChoiceQuestions where quiz_id = ?");
        statement.setLong(1, SAMPLE_MCQ_QUIZ_ID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();

        while (resultSet.next()) {
            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
            mcq.setQuestionId(resultSet.getInt("id"));
            mcq.setQuizId(resultSet.getLong("quiz_id"));
            mcq.setQuestion(resultSet.getString("question"));
            mcq.setCorrectAnswer(resultSet.getString("correctAnswer"));

            int numIncorrectAnswers = resultSet.getInt("numIncorrectAnswers");
            List<String> incorrectAnswers = new ArrayList<>();
            for (int i = 0; i < numIncorrectAnswers; i++) {
                incorrectAnswers.add(resultSet.getString("incorrectAnswer" + (i+1)));
            }
            mcq.setIncorrectAnswers(incorrectAnswers);

            questions.add(mcq);
        }

        assertTrue(findMCQ(sampleMCQ, questions));

        deleteQuestion(sampleMCQ);
    }

    private boolean findMCQ(MultipleChoiceQuestion mcq, List<MultipleChoiceQuestion> questions) {
        for (MultipleChoiceQuestion question : questions) {
            if (MultipleChoiceQuestion.equals(mcq, question)) {
                return true;
            }
        }

        return false;
    }
}

package DAO;

import database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import questions.PictureResponseQuestion;
import questions.Question;
import questions.ResponseQuestion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseQuestionDAOTest {
    private static Connection con;
    private static ResponseQuestionDAO rqd;
    private static ResponseQuestion rq1;
    private static ResponseQuestion rq2;
    private static ResponseQuestion rq3;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException, IOException {
        con = DBConnection.getConnection();
        //Here we want to reset Tables
        DBConnection.resetTables();

        rqd = new ResponseQuestionDAO(con);

        String question1 = "What is the capital of France ?";
        List<String> legalAnswers1 = new ArrayList<>();
        legalAnswers1.add("Paris");

        rq1 = new ResponseQuestion(question1, legalAnswers1, 1);

        String question2 = "Who wrote the play \"Romeo and Juliet\" ?";
        List<String> legalAnswers2 = new ArrayList<>();
        legalAnswers2.add("William Shakespeare");
        legalAnswers2.add("Shakespeare");

        rq2 = new ResponseQuestion(question2, legalAnswers2, 2);

        String question3 = "Name The President of the United States";
        List<String> legalAnswers3 = new ArrayList<>();
        legalAnswers3.add("George Washington");
        legalAnswers3.add("Abraham Lincoln");
        legalAnswers3.add("Lincoln");
        legalAnswers3.add("Donald Trump");
        legalAnswers3.add("Trump");

        rq3 = new ResponseQuestion(question3, legalAnswers3, 3);

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, hashedPassword, isAdmin, firstName, lastName) " +
                        "VALUES ('user1', 'psw', false, 'nm', 'ln');");
        PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO quizzes(author, quizName, type) VALUES (1, 'quiz1', 'default');");

        ps.execute();
        ps2.execute();
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException { DBConnection.resetTables(); }

    @Test
    public void testAddQuestion1() throws SQLException {
        rqd.addQuestion(rq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(rq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestionsAnswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(rq1.getLegalAnswers(), answers);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st1.setString(1, rq1.getQuestion());
        st1.execute();
    }

    @Test
    public void testAddQuestion2() throws SQLException {
        rqd.addQuestion(rq2, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(rq2.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestionsAnswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(rq2.getLegalAnswers(), answers);

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st2.setString(1, rq2.getQuestion());
        st2.execute();
    }

    @Test
    public void testAddQuestion3() throws SQLException {
        //question 1
        rqd.addQuestion(rq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(rq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM ResponseQuestionsAnswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(rq1.getLegalAnswers(), answers);

        //question 2
        rqd.addQuestion(rq2, 1);
        PreparedStatement statement2 = con.prepareStatement(
                "SELECT * FROM ResponseQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result2 = statement2.executeQuery();

        result2.last();
        String text2 = result2.getString("question");
        long questionId2 = result2.getLong("id");
        assertEquals(rq2.getQuestion(), text2);

        List<String> answers2 = new ArrayList<>();
        PreparedStatement statement_2 = con.prepareStatement(
                "SELECT * FROM ResponseQuestionsAnswers WHERE questionId=?;");
        statement_2.setLong(1, questionId2);
        ResultSet result_2 = statement_2.executeQuery();
        while (result_2.next()) answers2.add(result_2.getString("answer"));
        assertEquals(rq2.getLegalAnswers(), answers2);

        //question3
        rqd.addQuestion(rq3, 1);
        PreparedStatement statement3 = con.prepareStatement(
                "SELECT * FROM ResponseQuestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result3 = statement3.executeQuery();

        result3.last();
        String text3 = result3.getString("question");
        long questionId3 = result3.getLong("id");
        assertEquals(rq3.getQuestion(), text3);

        List<String> answers3 = new ArrayList<>();
        PreparedStatement statement_3 = con.prepareStatement(
                "SELECT * FROM ResponseQuestionsAnswers WHERE questionId=?;");
        statement_3.setLong(1, questionId3);
        ResultSet result_3 = statement_3.executeQuery();
        while (result_3.next()) answers3.add(result_3.getString("answer"));
        assertEquals(rq3.getLegalAnswers(), answers3);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st1.setString(1, rq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st2.setString(1, rq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st3.setString(1, rq3.getQuestion());
        st3.execute();
    }

    @Test
    public void testGetQuestions() throws SQLException {
        rqd.addQuestion(rq1, 1);
        rqd.addQuestion(rq2, 1);
        rqd.addQuestion(rq3, 1);

        List<Question> questions = rqd.getQuestions(1);

        assertEquals(questions.get(0).getQuestion(), rq1.getQuestion());
        assertEquals(questions.get(1).getQuestion(), rq2.getQuestion());
        assertEquals(questions.get(2).getQuestion(), rq3.getQuestion());


        List<String> legalAnswers1 = ((ResponseQuestion)questions.get(0)).getLegalAnswers();
        List<String> legalAnswers2 = ((ResponseQuestion)questions.get(1)).getLegalAnswers();
        List<String> legalAnswers3 = ((ResponseQuestion)questions.get(2)).getLegalAnswers();

        assertEquals(legalAnswers1, rq1.getLegalAnswers());
        assertEquals(legalAnswers2, rq2.getLegalAnswers());
        assertEquals(legalAnswers3, rq3.getLegalAnswers());

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st1.setString(1, rq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st2.setString(1, rq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM ResponseQuestions where question=?;");
        st3.setString(1, rq3.getQuestion());
        st3.execute();
    }


}
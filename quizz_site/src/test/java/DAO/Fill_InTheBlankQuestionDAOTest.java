package DAO;

import database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import questions.Fill_InTheBlankQuestion;
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

class Fill_InTheBlankQuestionDAOTest {
    private static Connection con;
    private static Fill_InTheBlankQuestionDAO fqd;
    private static Fill_InTheBlankQuestion fbq1;
    private static Fill_InTheBlankQuestion fbq2;
    private static Fill_InTheBlankQuestion fbq3;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException, IOException {
        con = DBConnection.getConnection();
        //reset tables
        DBConnection.resetTables();

        fqd = new Fill_InTheBlankQuestionDAO(con);

        String question1 = "bla bla ___ bla";
        List<String> legalAnswers1 = new ArrayList<>();
        legalAnswers1.add("ans1");

        fbq1 = new Fill_InTheBlankQuestion();
        fbq1.setQuestionId(1);
        fbq1.setQuestion(question1);
        fbq1.setLegalAnswers(legalAnswers1);

        String question2 = "bla bla bla ___ bla ___ bla bla";
        List<String> legalAnswers2 = new ArrayList<>();
        legalAnswers2.add("ans1");
        legalAnswers2.add("ans2");

        fbq2 = new Fill_InTheBlankQuestion();
        fbq2.setQuestionId(2);
        fbq2.setQuestion(question2);
        fbq2.setLegalAnswers(legalAnswers2);

        String question3 = "bla bla bla ___ bla ___" +
                "bla bla ___ bla bla bla ___ bla";
        List<String> legalAnswers3 = new ArrayList<>();
        legalAnswers3.add("ans1");
        legalAnswers3.add("ans2");
        legalAnswers3.add("ans3");
        legalAnswers3.add("ans4");

        fbq3 = new Fill_InTheBlankQuestion();
        fbq3.setQuestionId(3);
        fbq3.setQuestion(question3);
        fbq3.setLegalAnswers(legalAnswers3);

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, hashedPassword, isAdmin, firstName, lastName) VALUES ('un', " +
                        "'hp', false, 'fn', 'ln');");
        PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO quizzes(author, quizName) VALUES (1, 'quiz1');");

        ps.execute();
        ps2.execute();
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException { DBConnection.resetTables(); }

    @Test
    public void testAddQuestion1() throws SQLException {
        fqd.addQuestion(fbq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM fillintheblankquestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(fbq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM fillintheblankanswer WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(fbq1.getLegalAnswers(), answers);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM responsequestions where question=?;");
        st1.setString(1, fbq1.getQuestion());
        st1.execute();
    }

    @Test
    public void testAddQuestion2() throws SQLException {
        fqd.addQuestion(fbq2, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM fillintheblankquestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(fbq2.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM fillintheblankanswer WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(fbq2.getLegalAnswers(), answers);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st1.setString(1, fbq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st2.setString(1, fbq2.getQuestion());
        st2.execute();
    }

    @Test
    public void testAddQuestion3() throws SQLException {
        //question 1
        fqd.addQuestion(fbq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM fillintheblankquestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(fbq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM fillintheblankanswer WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(fbq1.getLegalAnswers(), answers);

        //question 2
        fqd.addQuestion(fbq2, 1);
        PreparedStatement statement2 = con.prepareStatement(
                "SELECT * FROM fillintheblankquestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result2 = statement2.executeQuery();

        result2.last();
        String text2 = result2.getString("question");
        long questionId2 = result2.getLong("id");
        assertEquals(fbq2.getQuestion(), text2);

        List<String> answers2 = new ArrayList<>();
        PreparedStatement statement_2 = con.prepareStatement(
                "SELECT * FROM fillintheblankanswer WHERE questionId=?;");
        statement_2.setLong(1, questionId2);
        ResultSet result_2 = statement_2.executeQuery();
        while (result_2.next()) answers2.add(result_2.getString("answer"));
        assertEquals(fbq2.getLegalAnswers(), answers2);

        //question3
        fqd.addQuestion(fbq3, 1);
        PreparedStatement statement3 = con.prepareStatement(
                "SELECT * FROM fillintheblankquestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result3 = statement3.executeQuery();

        result3.last();
        String text3 = result3.getString("question");
        long questionId3 = result3.getLong("id");
        assertEquals(fbq3.getQuestion(), text3);

        List<String> answers3 = new ArrayList<>();
        PreparedStatement statement_3 = con.prepareStatement(
                "SELECT * FROM fillintheblankanswer WHERE questionId=?;");
        statement_3.setLong(1, questionId3);
        ResultSet result_3 = statement_3.executeQuery();
        while (result_3.next()) answers3.add(result_3.getString("answer"));
        assertEquals(fbq3.getLegalAnswers(), answers3);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st1.setString(1, fbq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st2.setString(1, fbq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st3.setString(1, fbq3.getQuestion());
        st3.execute();
    }

    @Test
    public void testGetQuestions() throws SQLException {
        fqd.addQuestion(fbq1, 1);
        fqd.addQuestion(fbq2, 1);
        fqd.addQuestion(fbq3, 1);

        List<Question> questions = fqd.getQuestions(1);

        assertEquals(questions.get(0).getQuestion(), fbq1.getQuestion());
        assertEquals(questions.get(1).getQuestion(), fbq2.getQuestion());
        assertEquals(questions.get(2).getQuestion(), fbq3.getQuestion());


        List<String> legalAnswers1 = ((Fill_InTheBlankQuestion)questions.get(0)).getLegalAnswers();
        List<String> legalAnswers2 = ((Fill_InTheBlankQuestion)questions.get(1)).getLegalAnswers();
        List<String> legalAnswers3 = ((Fill_InTheBlankQuestion)questions.get(2)).getLegalAnswers();

        assertEquals(legalAnswers1, fbq1.getLegalAnswers());
        assertEquals(legalAnswers2, fbq2.getLegalAnswers());
        assertEquals(legalAnswers3, fbq3.getLegalAnswers());

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st1.setString(1, fbq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st2.setString(1, fbq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM fillintheblankquestions where question=?;");
        st3.setString(1, fbq3.getQuestion());
        st3.execute();
    }
}
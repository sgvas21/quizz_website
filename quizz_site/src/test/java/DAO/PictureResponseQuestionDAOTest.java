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

class PictureResponseQuestionDAOTest {
    private static Connection con;
    private static PictureResponseQuestionDAO pqd;
    private static PictureResponseQuestion pq1;
    private static PictureResponseQuestion pq2;
    private static PictureResponseQuestion pq3;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException, IOException {
        con = DBConnection.getConnection();
        DBConnection.resetTables();

        pqd = new PictureResponseQuestionDAO(con);

        String question1 = "What you see in the picture?";
        List<String> legalAnswers1 = new ArrayList<>();
        legalAnswers1.add("Chomolungma");
        legalAnswers1.add("Mount Everest");
        legalAnswers1.add("Everest");
        String picURL1 = "picURL_1";

        pq1 = new PictureResponseQuestion(question1, legalAnswers1, picURL1);

        String question2 = "Which president is this?";
        List<String> legalAnswers2 = new ArrayList<>();
        legalAnswers2.add("Abraham Lincoln");
        legalAnswers2.add("Lincoln");
        String picURL2 = "picURL_2";

        pq2 = new PictureResponseQuestion(question2, legalAnswers2, picURL2);

        String question3 = "In Which country is this statue located?";
        List<String> legalAnswers3 = new ArrayList<>();
        legalAnswers3.add("United State Of America");
        legalAnswers3.add("USA");
        legalAnswers3.add("America");
        String picURL3 = "picURL_3";

        pq3 = new PictureResponseQuestion(question3, legalAnswers3, picURL3);

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, hashedPassword, isAdmin, firstName, lastName) VALUES ('u1', " +
                        "'hp', false, 'fn', 'ln');");
        PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO quizzes(author, quizName, type) VALUES (1, 'quiz1', 'default');");

        ps.execute();
        ps2.execute();
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException { DBConnection.resetTables(); }

    @Test
    public void testAddQuestion1() throws SQLException {
        pqd.addQuestion(pq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(pq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestionsanswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(pq1.getLegalAnswers(), answers);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st1.setString(1, pq1.getQuestion());
        st1.execute();
    }

    @Test
    public void testAddQuestion2() throws SQLException {
        pqd.addQuestion(pq2, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(pq2.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestionsanswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(pq2.getLegalAnswers(), answers);

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st2.setString(1, pq2.getQuestion());
        st2.execute();
    }

    @Test
    public void testAddQuestion3() throws SQLException {
        //question 1
        pqd.addQuestion(pq1, 1);
        PreparedStatement statement1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result1 = statement1.executeQuery();

        result1.last();
        String text = result1.getString("question");
        long questionId1 = result1.getLong("id");
        assertEquals(pq1.getQuestion(), text);

        List<String> answers = new ArrayList<>();
        PreparedStatement statement_1 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestionsanswers WHERE questionId=?;");
        statement_1.setLong(1, questionId1);
        ResultSet result_1 = statement_1.executeQuery();
        while (result_1.next()) answers.add(result_1.getString("answer"));
        assertEquals(pq1.getLegalAnswers(), answers);

        //question 2
        pqd.addQuestion(pq2, 1);
        PreparedStatement statement2 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result2 = statement2.executeQuery();

        result2.last();
        String text2 = result2.getString("question");
        long questionId2 = result2.getLong("id");
        assertEquals(pq2.getQuestion(), text2);

        List<String> answers2 = new ArrayList<>();
        PreparedStatement statement_2 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestionsanswers WHERE questionId=?;");
        statement_2.setLong(1, questionId2);
        ResultSet result_2 = statement_2.executeQuery();
        while (result_2.next()) answers2.add(result_2.getString("answer"));
        assertEquals(pq2.getLegalAnswers(), answers2);

        //question3
        pqd.addQuestion(pq3, 1);
        PreparedStatement statement3 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestions;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result3 = statement3.executeQuery();

        result3.last();
        String text3 = result3.getString("question");
        long questionId3 = result3.getLong("id");
        assertEquals(pq3.getQuestion(), text3);

        List<String> answers3 = new ArrayList<>();
        PreparedStatement statement_3 = con.prepareStatement(
                "SELECT * FROM pictureresponsequestionsanswers WHERE questionId=?;");
        statement_3.setLong(1, questionId3);
        ResultSet result_3 = statement_3.executeQuery();
        while (result_3.next()) answers3.add(result_3.getString("answer"));
        assertEquals(pq3.getLegalAnswers(), answers3);

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st1.setString(1, pq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st2.setString(1, pq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st3.setString(1, pq3.getQuestion());
        st3.execute();
    }

    @Test
    public void testGetQuestions() throws SQLException {
        pqd.addQuestion(pq1, 1);
        pqd.addQuestion(pq2, 1);
        pqd.addQuestion(pq3, 1);

        List<Question> questions = pqd.getQuestions(1);

        assertEquals(questions.get(0).getQuestion(), pq1.getQuestion());
        assertEquals(questions.get(1).getQuestion(), pq2.getQuestion());
        assertEquals(questions.get(2).getQuestion(), pq3.getQuestion());


        List<String> legalAnswers1 = ((PictureResponseQuestion)questions.get(0)).getLegalAnswers();
        List<String> legalAnswers2 = ((PictureResponseQuestion)questions.get(1)).getLegalAnswers();
        List<String> legalAnswers3 = ((PictureResponseQuestion)questions.get(2)).getLegalAnswers();

        assertEquals(legalAnswers1, pq1.getLegalAnswers());
        assertEquals(legalAnswers2, pq2.getLegalAnswers());
        assertEquals(legalAnswers3, pq3.getLegalAnswers());

        PreparedStatement st1 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st1.setString(1, pq1.getQuestion());
        st1.execute();

        PreparedStatement st2 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st2.setString(1, pq2.getQuestion());
        st2.execute();

        PreparedStatement st3 = con.prepareStatement(
                "DELETE FROM pictureresponsequestions where question=?;");
        st3.setString(1, pq3.getQuestion());
        st3.execute();
    }

}
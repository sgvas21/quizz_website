package DAO;

import database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import questions.MultipleChoiceQuestion;
import questions.Question;
import questions.ResponseQuestion;
import user.Hash;
import user.User;
import quizz.quizz;
import quizz.randomQuizz;
import quizz.quizzAttempt;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizzDAOTest {
    private static User testUser;
    private static User attemtUser;
    private static UserDAO userDao;
    private static QuizzDAO quizDao;
    private static quizz quiz;
    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        testUser = new User("username", new Hash("pass").hashingPassword(),
                "firstname", "lastname", true);
        attemtUser = new User("trier", new Hash("password").hashingPassword(), "Gia", "Gnolidze", true);
        userDao = new UserDAO(DBConnection.getConnection());
        quizDao = new QuizzDAO(DBConnection.getConnection());

        userDao.createUser(testUser);

        String question1 = "Who is the president of USA?";
        List<String> legalAnswers1 = new ArrayList<>();
        legalAnswers1.add("Abraham Lincoln");
        legalAnswers1.add("Donald Trump");
        legalAnswers1.add("Trump");

        Question q1 = new ResponseQuestion(question1, legalAnswers1);

        String question2 = "Which country is the largest?";
        List<String> incorrectChoices = new ArrayList<>();
        String correctAnswer = "Russia";
        incorrectChoices.add("other1");
        incorrectChoices.add("other2");
        incorrectChoices.add("other3");

        Question q2 = new MultipleChoiceQuestion(question2, correctAnswer, incorrectChoices);

        List<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);

        quiz = new randomQuizz("quiz1", testUser, questions, new ArrayList<>());
        quizDao.addQuizz(quiz);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException {
        DBConnection.resetTables();
    }

    @Test
    public void testAdd() throws SQLException {
        quizz curr = quizDao.getQuizzById(quiz.getId());
        assertEquals(quiz.getId(), curr.getId());
        assertEquals("quiz1", curr.getName());
        assertEquals("username", curr.getAuthor().getUsername());
        assertEquals(2, curr.getQuestions().size());
    }

    @Test
    public void testQuizHistoryQuizRemove() throws SQLException {
        userDao.createUser(attemtUser);

        quizzAttempt attempt = new quizzAttempt(attemtUser.getId(), 69.69, new Timestamp(System.currentTimeMillis()));
        quizDao.addQuizzAttempt(attempt, quiz.getId());
        quizz curr = quizDao.getQuizzById(quiz.getId());
        assertEquals(1, curr.getHistory().size());
        assertEquals(1, quizDao.getQuizzes().size());

        List<quizzAttempt> attempts = quizDao.getQuizzAttemptsByUser(attemtUser.getId());
        assertEquals(1, attempts.size());

        quizDao.removeQuizById(quiz.getId());
        assertEquals(0, quizDao.getQuizzes().size());
    }

}
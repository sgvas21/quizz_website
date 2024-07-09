package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import DAO.QuizzDAO;
import DAO.UserDAO;
import database.DBConnection;
import quizz.quizz;
import response.AnswerResponse;
import user.User;
import questions.Question;
import user.UserAttemptResult;

@WebServlet("/QuizTakeServlet")
public class QuizTakeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        QuizzDAO quizDAO = (QuizzDAO)httpServletRequest.getServletContext().getAttribute("QuizDAO");

        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        try {
            quizz quiz = quizDAO.getQuizzById(quizId);
            httpServletRequest.setAttribute("questionList", quiz.getQuestions());
            httpServletRequest.setAttribute("quizId", quizId);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting quiz" + e.getMessage());
        }

        httpServletRequest.getRequestDispatcher("takeQuiz.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        QuizzDAO quizDAO = (QuizzDAO)httpServletRequest.getServletContext().getAttribute("QuizDAO");

        long userId = ((User)httpServletRequest.getSession().getAttribute("currUser")).getId();
        long quizId = Long.parseLong(httpServletRequest.getParameter("quizId"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        quizz quiz;
        try {
            quiz = quizDAO.getQuizzById(quizId);
        } catch (SQLException e) {
            throw new RuntimeException("Finding Quizz by ID failed: " + e.getMessage());
        }

        List<Question> questionList = quiz.getQuestions();
        double score = getScore(httpServletRequest, questionList);

        UserAttemptResult att= new UserAttemptResult(quizId, userId, score, timestamp);
        UserDAO userDAO;
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
            userDAO.addAttempt(att);
            httpServletRequest.setAttribute("user", userDAO.getUser(userId));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error While connecting to Database: " + e.getMessage());
        }

        httpServletRequest.setAttribute("score", score);
        httpServletRequest.setAttribute("max score", questionList.size());
        httpServletRequest.setAttribute("time spent", timestamp);

        httpServletRequest.getRequestDispatcher("quizResult.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Helper method
    private double getScore(HttpServletRequest httpServletRequest, List<Question> questionList) {
        double result = 0;
        for (int i = 0; i < questionList.size(); i++) {
            String[] individualAnswers = httpServletRequest.getParameterValues(String.valueOf(i));
            List<String> answersList = List.of(individualAnswers);

            result += questionList.get(i).getScore(new AnswerResponse(answersList));
        }

        return result;
    }
}

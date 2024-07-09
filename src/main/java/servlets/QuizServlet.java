package servlets;

import DAO.QuizzDAO;
import quizz.quizz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        long quizId = Long.parseLong(request.getParameter("quizId"));
        QuizzDAO quizDao = (QuizzDAO) request.getServletContext().getAttribute("QuizDAO");
        try {
            quizz quiz = quizDao.getQuizzById(quizId);
            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("quiz.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        long quizId = Long.parseLong(request.getParameter("quizId"));
        QuizzDAO quizzDAO = (QuizzDAO) request.getServletContext().getAttribute("QuizDAO");
        try {
            quizz quiz = quizzDAO.getQuizzById(quizId);
            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", quiz.getQuestions());
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

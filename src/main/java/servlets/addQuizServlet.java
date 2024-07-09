package servlets;

import questions.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addQuizServlet")
public class addQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("addQuiz.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizName = request.getParameter("name");
        String random = request.getParameter("random");
        Boolean isRandom = false;
        if (random == null) isRandom = true;
        int nQuestions = (int)Long.parseLong(request.getParameter("nQuestions"));

        List<Question> questions = new ArrayList<>();
        request.getSession().setAttribute("name", quizName);
        request.getSession().setAttribute("questions", questions);
        request.getSession().setAttribute("nQuestions", nQuestions);
        request.getSession().setAttribute("currQuestions", 0);
        request.getSession().setAttribute("isRandom", isRandom);
        request.getRequestDispatcher("questionTypes.jsp").forward(request, response);
    }
}

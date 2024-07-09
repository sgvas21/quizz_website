package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/QuestionTypesServlet")
public class QuestionTypesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentQuestions = (int) request.getSession().getAttribute("currQuestions");
        request.getSession().setAttribute("currQuestions", currentQuestions + 1);
        String type = request.getParameter("type");
        request.getSession().setAttribute("type", type);
        request.getRequestDispatcher("question.jsp").forward(request, response);
    }
}

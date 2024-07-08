package servlets;

import DAO.UserDAO;
import user.Hash;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import quizz.quizz;
import user.UserAttemptResult;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {super.init();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("UserDAO");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDao.getUser(username);
            if (user != null && user.getPassword().equals(new Hash(password).hashingPassword())) {
                List<User> friendList = new ArrayList<>(userDao.getFriends(user.getId()));
                List<quizz> createdQuizzes = userDao.getCreatedQuizzes(user.getId());
                List<UserAttemptResult> attemptResults = userDao.getAttempts(user.getId());

                request.getSession().setAttribute("currUser", user);
                request.setAttribute("user", user);
                request.setAttribute("friendList", friendList);
                request.setAttribute("createdQuizzes", createdQuizzes);
                request.setAttribute("attempts", attemptResults);
                request.getRequestDispatcher("myProfile.jsp").forward(request, response);
            } else {
                request.setAttribute("text", "Username or password is incorrect, try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error accessing user data", e);
        }
    }
}

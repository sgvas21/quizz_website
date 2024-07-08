package servlets;

import DAO.RequestDAO;
import DAO.UserDAO;
import com.mysql.cj.log.Log;
import database.DBConnection;
import mails.request;
import user.User;
import user.UserAttemptResult;
import quizz.quizz;
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

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDAO userDao = null;
        List<User> friendList = null;
        List<quizz> createdQuizzes = null;
        List<UserAttemptResult> attemptResults = null;
        try {
            userDao = new UserDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        User user = null;
        try {
            user = userDao.getUser(request.getParameter("username"));
            if (user != null) {
                Set<User> tmp = userDao.getFriends(user.getId());
                friendList = new ArrayList<>(tmp);
                createdQuizzes = userDao.getCreatedQuizzes(user.getId());
                attemptResults = userDao.getAttempts(user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user == null) {
            response.sendRedirect("pageNotFound.jsp");
        } else {
            request.setAttribute("user", user);
            request.setAttribute("friendList", friendList);
            request.setAttribute("createdQuizzes", createdQuizzes);
            request.setAttribute("attempts", attemptResults);
            request.getRequestDispatcher("user.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestDAO dao = (RequestDAO)request.getServletContext().getAttribute("RequestDAO");
        long userId = Long.parseLong(request.getParameter("userId"));
        String username = request.getParameter("username");
        User currUser = (User)request.getSession().getAttribute("currUser");
        if (request.getParameter("sendReq") != null) {
            try {
                dao.addRequest(new request(currUser.getId(), userId));
                response.sendRedirect("/UserServlet?username=" + username);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (request.getParameter("respondReq") != null) {
            response.sendRedirect("/FriendRequestsServlet?userId=" + userId);
        }
    }
}

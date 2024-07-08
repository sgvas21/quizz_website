package servlets;

import DAO.MessageDAO;
import DAO.UserDAO;
import mails.message;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/MessengerServlet")
public class MessengerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("UserDAO");
        if (request.getSession().getAttribute("currUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        long fromId = Long.parseLong(request.getParameter("from_user_id"));
        User user = null;
        try {
            user = userDAO.getUser(fromId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("messenger.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageDAO messageDAO = (MessageDAO) request.getServletContext().getAttribute("MessageDAO");
        message msg = new message(Long.parseLong(request.getParameter("from_user_id")), Long.parseLong(request.getParameter("to_user_id")),
                request.getParameter("msg_text"), new Timestamp(System.currentTimeMillis()));
        try {
            messageDAO.addMessage(msg);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/MessengerServlet?from_user_id=" + request.getParameter("to_user_id"));
    }
}

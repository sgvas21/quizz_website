package servlets;

import DAO.RequestDAO;
import DAO.UserDAO;
import user.User;
import mails.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FriendRequestsServlet")
public class FriendRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve current user from session
        User currentUser = (User) request.getSession().getAttribute("currUser");

        // Retrieve DAOs from ServletContext attributes
        RequestDAO requestDAO = (RequestDAO) request.getServletContext().getAttribute("RequestDAO");
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("UserDAO");

        try {
            // Get requests for the current user
            List<request> requests = requestDAO.getRequestsByUser(currentUser.getId());

            // Retrieve user information for each request sender
            List<User> friendRequests = new ArrayList<>();
            for (request currRequest : requests) {
                User requester = userDao.getUser(currRequest.getFromId());
                friendRequests.add(requester);
            }

            // Set attribute for friend requests and forward to JSP
            request.setAttribute("reqs", friendRequests);
            request.getRequestDispatcher("friendRequests.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching friend requests", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Parse userId from request parameter
        long userId = Long.parseLong(request.getParameter("userId"));

        // Retrieve current user from session
        User currentUser = (User) request.getSession().getAttribute("currUser");

        // Retrieve DAOs from ServletContext attributes
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("UserDAO");
        RequestDAO friendRequestDao = (RequestDAO) request.getServletContext().getAttribute("RequestDAO");

        try {
            // Check if the "accept" parameter is present
            if (request.getParameter("accept") != null) {
                // Add both users as friends in the DAO
                userDao.addFriend(currentUser.getId(), userId);
                userDao.addFriend(userId, currentUser.getId());
            }

            // Remove the friend request from the DAO
            friendRequestDao.removeRequest(new request(userId, currentUser.getId()));

            // Redirect to FriendRequestsServlet after processing
            response.sendRedirect("/FriendRequestsServlet");
        } catch (SQLException e) {
            // Handle SQL exception
            throw new RuntimeException("Error processing friend request", e);
        }

    }
}

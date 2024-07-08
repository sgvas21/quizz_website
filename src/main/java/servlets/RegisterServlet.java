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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {super.init();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("UserDAO");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        try {
            User existingUser = userDao.getUser(username);
            //String currUsername = existingUser.getUsername();

            if (existingUser != null) {
                request.setAttribute("text", "User already exists, try a different username.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                User newUser = new User(username, new Hash(password).hashingPassword(), firstname, lastname, false);
                userDao.createUser(newUser);
                request.getSession().setAttribute("currUser", newUser);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while processing registration", e);
        }

    }
}

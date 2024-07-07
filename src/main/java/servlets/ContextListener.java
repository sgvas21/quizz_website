package servlets;

import DAO.MessageDAO;
import DAO.QuizzDAO;
import DAO.RequestDAO;
import DAO.UserDAO;
import database.DBConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserDAO userDao = null;
        QuizzDAO quizDao = null;
        MessageDAO messageDao = null;
        RequestDAO requestDao = null;

        try {
            userDao = new UserDAO(DBConnection.getConnection());
            quizDao = new QuizzDAO(DBConnection.getConnection());
            messageDao = new MessageDAO(DBConnection.getConnection());
            requestDao = new RequestDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        event.getServletContext().setAttribute("index", 0);
        event.getServletContext().setAttribute("UserDAO", userDao);
        event.getServletContext().setAttribute("QuizDAO", quizDao);
        event.getServletContext().setAttribute("MessageDAO", messageDao);
        event.getServletContext().setAttribute("RequestDAO", requestDao);
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) { }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute("currUser", null);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        event.getSession().setAttribute("currUser", null);
    }
}

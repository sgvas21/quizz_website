package servlets;

import database.DBConnection;
import questions.*;
import user.User;
import quizz.*;
import DAO.QuizzDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<Question> questions =(List<Question>) httpServletRequest.getSession().getAttribute("questions");
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        int curr =  (int) httpServletRequest.getSession().getAttribute("currQuestions");
        int number = (int) httpServletRequest.getSession().getAttribute("nQuestions");
        String type = (String) httpServletRequest.getSession().getAttribute("type");

        String text;
        int nLegalAnswers;
        String image;

        String[] ans = httpServletRequest.getParameterValues("answer");
        List<String> answers;
        if(ans != null){
            answers = new ArrayList<>(List.of(ans));
            text = (String) httpServletRequest.getSession().getAttribute("qText");
            switch (type){
                case  "ResponseQuestion":
                    ResponseQuestion responseQuestion = new ResponseQuestion(text, answers);
                    questions.add(responseQuestion);
                    break;
                case  "PictureResponseQuestion":
                    image = (String) httpServletRequest.getSession().getAttribute("image");
                    PictureResponseQuestion prq = new PictureResponseQuestion(text, answers, image);
                    questions.add(prq);
                    break;
            }

            httpServletRequest.getSession().setAttribute("questions", questions);
            if(curr == number){
                addQuiz(httpServletRequest, httpServletResponse);
                httpServletRequest.setAttribute("user", user);
                httpServletRequest.getSession().setAttribute("questions", null);
                httpServletResponse.sendRedirect( "/UserServlet?username=" +user.getUsername());
            } else {
                httpServletRequest.getRequestDispatcher("questionTypes.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } else {
            if(type.equals("ResponseQuestion")){
                nLegalAnswers = (int) Long.parseLong(httpServletRequest.getParameter("nLegalAnswers"));
                httpServletRequest.setAttribute("nLegalAnswers", nLegalAnswers);
            }
            if(type.equals("PictureResponseQuestion")){
                image =  httpServletRequest.getParameter("image");
                httpServletRequest.getSession().setAttribute("image", image);

                nLegalAnswers = (int) Long.parseLong(httpServletRequest.getParameter("nLegalAnswers"));
                httpServletRequest.setAttribute("nLegalAnswers", nLegalAnswers);
            }
            else {
                nLegalAnswers = 6;
                httpServletRequest.setAttribute("nLegalAnswers", nLegalAnswers);
            }

            httpServletRequest.setAttribute("type", type);
            text = httpServletRequest.getParameter("qText");
            httpServletRequest.getSession().setAttribute("qText", text);
            httpServletRequest.getRequestDispatcher("getAnswers.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    private void addQuiz(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Boolean isRandom = (Boolean) httpServletRequest.getSession().getAttribute("isRandom");
        User user = (User) httpServletRequest.getSession().getAttribute("currUser");
        String quizName = (String) httpServletRequest.getSession().getAttribute("name");
        List<Question> questions =(List<Question>) httpServletRequest.getSession().getAttribute("questions");
        quizz quiz;
        if(isRandom){
            quiz = new randomQuizz(quizName, user, questions, new ArrayList<>());
        } else {
            quiz = new defaultQuizz(quizName, user, questions, new ArrayList<>());
        }
        try {
            QuizzDAO quizDAO = new QuizzDAO(DBConnection.getConnection());
            quizDAO.addQuizz(quiz);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
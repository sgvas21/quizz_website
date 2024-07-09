<%@ page import="user.User" %>
<%@ page import="DAO.QuizzDAO" %>
<%@ page import="database.DBConnection" %>
<%@ page import="quizz.quizz" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recent Quizzes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<style>
    .navbar-light-custom {
        background-color: #f0f0f0 !important;
    }

    .navbar-light-custom .navbar-brand,
    .navbar-light-custom .navbar-nav .nav-link {
        color: green !important;
    }
</style>


<body style="background: #ffffff;">
<%
    QuizzDAO quizDao = new QuizzDAO(DBConnection.getConnection());
    List<quizz> quizzes = quizDao.getQuizzes();
    String username = "";
    long currUserId = -1;
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
    } else {
        username = ((User)session.getAttribute("currUser")).getUsername();
        currUserId = ((User)session.getAttribute("currUser")).getId();
    }
%>
<nav class="navbar navbar-expand-lg navbar-light navbar-light-custom" style="margin-top: 0; margin-bottom: 1.5%;">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/quizzes.jsp">Quiz Website</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-between" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active"
               href="<%= request.getContextPath() %>/UserServlet?username=<%= username %>">My Profile <span
                    class="sr-only">(current)</span></a>
            <a class="nav-item nav-link"
               href="<%= request.getContextPath() %>/FriendRequestsServlet?userId=<%= currUserId %>">Friend Requests</a>
            <a class="nav-item nav-link"
               href="<%= request.getContextPath() %>/addQuizServlet?userId=<%= currUserId %>">Create Quiz</a>
        </div>
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="<%= request.getContextPath() %>/LogoutServlet">Sign out</a>
        </div>
    </div>
</nav>

<%!
    // Helper method to open a container and row
    void openContainerAndRow(JspWriter out) throws IOException {
        out.println("<div class='container'>");
        out.println("<div class='row'>");
    }

    // Helper method to close a container and row
    void closeContainerAndRow(JspWriter out) throws IOException {
        out.println("</div></div>");
    }

    // Helper method to create a quiz card
    void createQuizCard(JspWriter out, quizz quiz, String contextPath) throws IOException {
        out.println("<div class='col-lg-4 mb-4'>");
        out.println("<div class='card'>");
        out.println("<img src='https://cdn.dribbble.com/users/1228532/screenshots/6559242/quiz-logo_4x.png' class='card-img-top'>");
        out.println("<div class='card-body'>");
        out.println("<h5 class='card-title'>" + quiz.getName() + "</h5>");
        out.println("<a href='" + contextPath + "/UserServlet?username=" + quiz.getAuthor().getUsername() + "' class='card-text' style='color: green;'>" + quiz.getAuthor().getUsername() + "</a>");
        out.println("<a href='" + contextPath + "/QuizServlet?quizId=" + quiz.getId() + "' class='btn btn-outline-success btn-sm'>Quiz Page</a>");
        out.println("</div></div></div>");
    }

    // Helper method to create the "Create New" card
    void createNewQuizCard(JspWriter out, String contextPath) throws IOException {
        out.println("<div class='col-lg-4 mb-4'>");
        out.println("<div class='card'>");
        out.println("<img src='https://cdn.dribbble.com/users/1228532/screenshots/6559242/quiz-logo_4x.png' class='card-img-top'>");
        out.println("<div class='card-body'>");
        out.println("<h5 class='card-title'></h5>");
        out.println("<a href='" + contextPath + "/addQuizServlet' class='btn btn-outline-success btn-sm'>Create New</a>");
        out.println("</div></div></div>");
    }
%>

<%
    int counter = 0;
    for (quizz quiz : quizzes) {
        if (counter % 3 == 0) {
            openContainerAndRow(out);
        }

        createQuizCard(out, quiz, request.getContextPath());

        if (counter % 3 == 2) {
            closeContainerAndRow(out);
        }
        counter++;
    }

    // Adding the "Create New" card
    if (counter % 3 == 0) {
        openContainerAndRow(out);
    }
    createNewQuizCard(out, request.getContextPath());
    if (counter % 3 == 2) {
        closeContainerAndRow(out);
    }
%>

</body>
</html>
<%@ page import="user.User" %>
<%@ page import="quizz.quizz" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Result</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%
    User currUser = (User) session.getAttribute("currUser");
    if (currUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer maxScore = (Integer) request.getAttribute("max score");
    Double score = (Double) request.getAttribute("score");

%>
<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">
                    <h2><%= score %> / <%= maxScore %></h2>
                    <a href="quizzes.jsp">Return To Quizzes Page</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Friend Requests</title>
</head>
<body>

<style>

    .navbar-light-custom {
        background-color: #f0f0f0 !important;
    }

    .navbar-light-custom .navbar-brand,
    .navbar-light-custom .navbar-nav .nav-link {
        color: green !important;
    }

    .card {
        background-color: darkseagreen;
        padding: 180px;
    }


</style>

<%
    long currUserId = -1;
    String currUsername = "";
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
    } else {
        currUsername = ((User)session.getAttribute("currUser")).getUsername();
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
               href="<%= request.getContextPath() %>/UserServlet?username=<%= currUsername %>">My Profile <span
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

<div class="container py-5">
    <div class="row d-flex justify-content-center align-items-center">
        <div class="card shadow-2-strong" style="border-radius: 1rem; width: 80%; min-height: 80%;">
            <div class="card-body p-5 text-center">
                <%
                    List<User> reqs = (List<User>) request.getAttribute("reqs");
                    if (reqs != null) {
                        for (User u : reqs) {
                %>
                <div>
                    <li><a href='/UserServlet?username=<%= u.getUsername() %>'><%= u.getUsername() %></a></li>
                    <form action='FriendRequestsServlet' method='post'>
                        <input type='hidden' name='userId' value='<%= u.getId() %>'>
                        <button class='btn btn-success' type='submit' name='accept' value='accept'>Accept</button>
                        <button class='btn btn-danger' type='submit' name='decline' value='delete'>Decline</button>
                    </form>
                </div>
                <%
                        }
                    } else {
                        return;
                    }
                    if (reqs.isEmpty()) {
                %>
                <h3 style="color: antiquewhite;">No friend requests</h3>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>



<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
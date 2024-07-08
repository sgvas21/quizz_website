<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="quizz.quizz" %>
<%@ page import="user.UserAttemptResult" %>
<%@ page import="DAO.QuizzDAO" %>
<%@ page import="mails.request" %>
<%@ page import="DAO.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style>
        /* Existing styles for light mode */
        .navbar-light-custom {
            background-color: #f0f0f0 !important;
        }

        .navbar-light-custom .navbar-brand,
        .navbar-light-custom .navbar-nav .nav-link {
            color: green !important;
        }

        body {
            color: green !important; /* All text on the page in green */
        }

        /* Style for the message box */
        .message-box {
            background-color: lightgreen;
            border: 1px solid green;
            padding: 10px;
            margin-bottom: 15px;
        }

        /* Style for the "Message" button */
        .btn-outline-primary {
            color: green !important; /* Text color green */
            border-color: green !important; /* Border color green */
        }

        .btn-outline-primary:hover {
            background-color: green !important; /* Hover background color green */
            color: white !important; /* Hover text color white */
        }

        /* Style for the profile picture */
        .profile-picture {
            background-color: green; /* Background color green */
        }

        /* Style for card backgrounds */
        .card {
            background-color: #f0f0f0; /* Set background color for all cards */
        }

        .friend-username {
            color: green;
        }
    </style>
</head>
<body class="<%= session.getAttribute("darkMode") != null && session.getAttribute("darkMode").equals("dark") ? "dark-mode" : "" %>">

<%-- User session validation --%>
<%
    boolean isCurrUserAdmin = false;
    long currUserId = -1;
    String currUsername = "";
    String currName = "";

    if (session.getAttribute("currUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    } else {
        currName = ((User) session.getAttribute("currUser")).getFirstname() + " " + ((User) session.getAttribute("currUser")).getLastname();
        isCurrUserAdmin = ((User) session.getAttribute("currUser")).hasAdminPrivileges();
        currUsername = ((User) session.getAttribute("currUser")).getUsername();
        currUserId = ((User) session.getAttribute("currUser")).getId();
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

<div class="container">
    <div class="main-body">
        <div class="row">
            <div class="col-lg-4">
                <div class="card">
                    <div class="card" style="min-height:200px;">
                        <div class="card profile-card">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img src="https://icon-library.com/images/white-profile-icon/white-profile-icon-24.jpg"
                                         class="rounded-circle p-1 profile-picture" width="110">
                                    <div class="mt-3">
                                        <h4><%=currUsername%></h4>
                                        <p class="text-secondary mb-1"><%=currName%></p>
                                        <a href="<%=request.getContextPath()%>/MessengerServlet?from_user_id=<%=currUserId%>"
                                           class="btn btn-outline-primary">Message</a>
                                    </div>
                                </div>
                                <hr class="my-4">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <div class="mt-3">
                                        <%if (isCurrUserAdmin) {%>
                                        <p class="text-secondary mb-1">Admin</p>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card mt-4">
                    <div class="card" style="min-height:200px;">
                        <div class="card-body">
                            <h5 class="d-flex align-items-center mb-3">Friends</h5>
                            <% List<User> friendlist = (List<User>) request.getAttribute("friendList");
                                if (friendlist != null) {
                                    for (User u : friendlist) { %>
                            <div class="col">
                                <a class="mb-0 friend-username" href="UserServlet?username=<%= u.getUsername() %>"><%= u.getUsername() %></a>
                            </div>
                            <%  }} %>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card mb-4">
                    <div class="card" style="min-height:334px;">
                        <div class="card-body">
                            <h5 class="d-flex align-items-center mb-3">Attempted Quizzes</h5>
                            <div class="row mb-3">
                                <div class="col">
                                    <label class="mb-0">Quiz Name</label>
                                </div>
                                <div class="col text-secondary">
                                    Score
                                </div>
                                <div class="col text-secondary">
                                    Attempt Time
                                </div>
                            </div>
                            <% List<UserAttemptResult> attempts = (List<UserAttemptResult>) request.getAttribute("attempts");
                                if (attempts != null) {
                                    for (UserAttemptResult q : attempts) {
                                        QuizzDAO dao = (QuizzDAO) request.getServletContext().getAttribute("QuizDao");
                                        quizz quiz = dao.getQuizzById(q.getQuizId()); %>
                            <div class="row mb-3">
                                <div class="col">
                                    <a class="mb-0" href="QuizServlet?quizId=<%= quiz.getId() %>"><%= quiz.getName() %></a>
                                </div>
                                <div class="col text-secondary"><%= q.getScore() %></div>
                                <div class="col text-secondary"><%= q.getTimeSpent() %></div>
                            </div>
                            <%  }} %>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card" style="min-height:200px;">
                        <div class="card-body">
                            <h5 class="d-flex align-items-center mb-3">Created Quizzes</h5>
                            <% List<quizz> quizzes = (List<quizz>) request.getAttribute("createdQuizzes");
                                if (quizzes != null) {
                                    for (quizz q : quizzes) { %>
                            <div class="row mb-3">
                                <div class="col">
                                    <a class="mb-0" href="QuizServlet?quizId=<%= q.getId() %>"><%= q.getName() %></a>
                                </div>
                            </div>
                            <%  }} %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

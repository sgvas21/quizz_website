<%@ page import="user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="quizz.quizz" %>
<%@ page import="user.UserAttemptResult" %>
<%@ page import="DAO.QuizzDAO" %>
<%@ page import="mails.request" %>
<%@ page import="DAO.UserDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.RequestDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<%
    boolean isCurrUserAdmin;
    long currUserId;
    String currUsername;
    User currUser;
    if(session.getAttribute("currUser") == null)  {
        response.sendRedirect("login.jsp");
        return;
    } else {
        currUser = (User)session.getAttribute("currUser");
        isCurrUserAdmin = currUser.hasAdminPrivileges();
        currUsername = currUser.getUsername();
        currUserId = currUser.getId();
    }
    User user = (User) request.getAttribute("user");
    String username = "";
    String name = "";
    long userId = -1;
    boolean isAdmin = false;
    if (user != null) {
        isAdmin = user.hasAdminPrivileges();
        username = user.getUsername();
        name = user.getFirstname() + " " + user.getLastname();
        userId = user.getId();
    } else {
        response.sendRedirect("pageNotFound.jsp");
    }
    if (username.equals(currUsername)) {
        request.getRequestDispatcher("myProfile.jsp").forward(request,response);
        return;
    }
    List<User> friendList = (List<User>) request.getAttribute("friendList");

    RequestDAO friendRequestDao = (RequestDAO) request.getServletContext().getAttribute("RequestDAO");
    UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("UserDAO");

    List<request> requests = friendRequestDao.getRequestsByUser(currUserId);
    List<User> friendReqs= new ArrayList<User>();
    for (request req : requests) {
        friendReqs.add(userDao.getUser(req.getFromId()));
    }

    List<request> requests1 = friendRequestDao.getRequestsByUser(userId);
    List<User> friendReqs1 = new ArrayList<User>();
    for (request req : requests1) {
        friendReqs1.add(userDao.getUser(req.getFromId()));
    }
%>

<nav class="navbar navbar-expand-lg navbar-light navbar-light-custom" style="margin-top: 0; margin-bottom: 1.5%;">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/UserServlet?username=<%= currUsername %>">Quiz Website</a>
    <div class="collapse navbar-collapse justify-content-between" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active"
               href="<%= request.getContextPath() %>/UserServlet?username=<%= currUsername %>">My Profile <span
                    class="sr-only">(current)</span></a>
            <a class="nav-item nav-link"
               href="<%= request.getContextPath() %>/FriendRequestsServlet?userId=<%= currUserId %>">Friend Requests</a>
            <a class="nav-item nav-link"
               href="<%= request.getContextPath() %>/addQuizServlet?userId=<%= currUserId %>">Create Quiz</a>
            <a class="nav-item nav-link"
               href="<%= request.getContextPath() %>/quizzes.jsp">Recent Quizzes</a>
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
                <div class="card mb-4">
                    <div class="card profile-card">
                        <div class="card" style="min-height:200px;">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <img src="https://icon-library.com/images/white-profile-icon/white-profile-icon-24.jpg"
                                         class="rounded-circle p-1 profile-picture" width="110">
                                    <div class="mt-3">
                                        <h4><%=username%></h4>
                                        <p class="text-secondary mb-1"><%=name%></p>
                                        <form action="UserServlet" method="post">
                                            <input type="hidden" name="userId" value="<%=userId%>">
                                            <input type="hidden" name="username" value="<%=username%>">
                                            <%-- System.out.println(friendList); --%>
                                            <% if (friendReqs1.contains(currUser)) { %>
                                            <label>Friend Request Sent</label>
                                            <% } else if (friendReqs.contains(user)) { %>
                                            <input type="submit" name="respondReq" class="btn btn-primary"
                                                   value="Respond Request">
                                            <% } else if (!friendList.contains(currUser)) { %>
                                            <input type="submit" name="sendReq" class="btn btn-primary"
                                                   value="Add Friend">
                                            <% } %>
                                        </form>
                                        <a href="<%=request.getContextPath()%>/MessengerServlet?from_user_id=<%=userId%>"
                                           class="btn btn-outline-primary">Message</a>
                                    </div>
                                </div>
                                <hr class="my-4">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <div class="mt-3">
                                        <% if (isAdmin) { %>
                                        <p class="text-secondary mb-1">Admin</p>
                                        <% } %>
                                    </div>
                                </div>

                                <% if (isCurrUserAdmin && !isAdmin) { %>
                                <div class="d-flex flex-column align-items-center text-center">
                                    <div class="mt-3">
                                        <form action="AdminServlet" method="post">
                                            <input type="hidden" name="userId" value="<%=userId%>">
                                            <input name="makeAdmin" value="Make Admin" type="submit"
                                                   class="btn btn-success">
                                            <input name="deleteUser" class="btn btn-danger" type="submit"
                                                   value="Delete User">
                                        </form>

                                    </div>
                                </div>
                                <% } else if (isCurrUserAdmin) { %>
                                <div class="d-flex flex-column align-items-center text-center">
                                    <div class="mt-3">
                                        <form action="AdminServlet" method="post">
                                            <input type="hidden" name="userId" value="<%=userId%>">
                                            <input name="deleteUser" class="btn btn-danger" type="submit"
                                                   value="Delete User">
                                        </form>
                                    </div>
                                </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card profile-card">
                        <div class="card" style="min-height:200px;">
                            <div class="card-body">
                                <h5 class="d-flex align-items-center mb-3">Friends</h5>
                                <% if (friendList != null) {
                                    for (User u : friendList) { %>
                                <div class="col">
                                    <a class="mb-0 friend-username"
                                       href="UserServlet?username=<%= u.getUsername() %>"><%= u.getUsername() %></a>
                                </div>
                                <% }
                                } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <div class="card mb-4">
                    <div class="card profile-card">
                        <div class="card" style="min-height:346px;">
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
                                            QuizzDAO dao = (QuizzDAO)request.getServletContext().getAttribute("QuizDAO");
                                            quizz quiz = dao.getQuizzById(q.getQuizId());
                                            out.println("<div class='row mb-3'>");
                                            out.println("<div class='col'>");
                                            out.println("<a class='mb-0' href='QuizServlet?quizId=" + quiz.getId() + "'>" + quiz.getName() + "</a>");
                                            out.println("</div>");
                                            out.println("<div class='col text-secondary'>" + q.getScore() + "</div>");
                                            out.println("<div class='col text-secondary'>" + q.getTimeSpent() + "</div>");
                                            out.println("</div>");
                                        }
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card profile-card">
                        <div class="card" style="min-height:200px;">
                            <div class="card-body">
                                <h5 class="d-flex align-items-center mb-3">Created Quizzes</h5>
                                <% List<quizz> quizzes = (List<quizz>) request.getAttribute("createdQuizzes");
                                    if (quizzes != null) {
                                        for (quizz q : quizzes) {
                                            out.println("<div class='row mb-3'>");
                                            out.println("<div class='col'>");
                                            out.println("<a class='mb-0' href='QuizServlet?quizId=" + q.getId() + "'>" + q.getName() + "</a>");
                                            out.println("</div>");
                                            out.println("</div>");
                                        }
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<style type="text/css">
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

</script>
</body>
</html>
</body>
</html>

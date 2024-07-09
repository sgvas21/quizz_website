<%@ page import="quizz.quizz" %>
<%@ page import="user.User" %>
<%@ page import="quizz.quizzAttempt" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.UserDAO" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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

    .profile-picture {
        background-color: green; /* Background color green */
    }

    body{
        background: white;
    }
    .card {
        position: relative;
        display: flex;
        flex-direction: column;
        min-width: 0;
        word-wrap: break-word;
        background-color: whitesmoke;
        background-clip: border-box;
        border: 0 solid transparent;
        border-radius: .25rem;
        margin-bottom: 1.5rem;
        box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
    }
    .me-2 {
        margin-right: .5rem!important;
    }

    .text-secondary {
        color: green !important; /* Override secondary text color to green */
    }

    h4, h5, h6, p, a, span, label {
        color: green !important; /* Make all text green */
    }
</style>

    <%!
    // Helper method to get the current user from session
    User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currUser");
    }

    // Helper method to check if the user is logged in, redirect if not
    boolean checkUserLoggedIn(User user, HttpServletResponse response) throws IOException {
        if (user == null) {
            response.sendRedirect("login.jsp");
            return false;
        }
        return true;
    }

    // Helper method to get quiz from request and handle null quiz
    quizz getQuiz(HttpServletRequest request, HttpServletResponse response) throws IOException {
        quizz quiz = (quizz) request.getAttribute("quiz");
        if (quiz == null) {
            response.sendRedirect("quizzes.jsp");
            return null;
        }
        return quiz;
    }

    // Helper method to get current user ID
    int getCurrentUserId(User user) {
        return (int) user.getId();
    }
%>

        <%
    User currUser = getCurrentUser(session);
    if (!checkUserLoggedIn(currUser, response)) {
        return;
    }

    boolean isCurrUserAdmin = currUser.hasAdminPrivileges();
    quizz quiz = getQuiz(request, response);
    if (quiz == null) {
        return;
    }

    String quizName = quiz.getName();
    User user = quiz.getAuthor();
    int currUserId = getCurrentUserId(currUser);
%>
    <nav class="navbar navbar-expand-lg navbar-light navbar-light-custom" style="margin-top: 0; margin-bottom: 1.5%;">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/UserServlet?username=<%= currUser.getUsername() %>">Quiz Website</a>
        <div class="collapse navbar-collapse justify-content-between" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active"
                   href="<%= request.getContextPath() %>/UserServlet?username=<%= currUser.getUsername() %>">My Profile <span
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
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <img src="https://icon-library.com/images/white-profile-icon/white-profile-icon-24.jpg" class="rounded-circle p-1 profile-picture" width="110">
                                <div class="mt-3">
                                    <h4><%=quizName%></h4>
                                    <a href="<%=request.getContextPath()%>/UserServlet?username=<%=user.getUsername()%>" class="text-secondary mb-1">By: <%=user.getUsername()%></a>
                                    <br>max score: <%=quiz.getQuestions().size()%>
                                </div>
                                <form action="QuizTakeServlet" method = 'get'>
                                    <input type="hidden" name="quizId" value=<%=quiz.getId()%>>
                                    <input name="startAttempt" value="Start Attempt" type="submit" class="btn btn-success">
                                </form>

                            </div>
                            <hr class="my-4">


                            <%if (isCurrUserAdmin) {%>
                            <div class="d-flex flex-column align-items-center text-center">
                                <div class="mt-3">
                                    <form action="AdminServlet" method="post">
                                        <input type="hidden" name="quizId" value="<%=quiz.getId()%>">
                                        <input name="deleteQuiz" value="Delete Quiz" type="submit" class="btn btn-danger">
                                    </form>
                                </div>
                            </div>
                            <%}%>

                        </div>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="card" style="min-height: 353px;">
                        <div class="card-body">
                            <h5 class="d-flex align-items-center mb-3">Quiz History</h5>
                            <div class="row mb-3">
                                <div class="col">
                                    <label class="mb-0">User</label>
                                </div>
                                <div class="col text-secondary">
                                    Score
                                </div>
                                <div class="col text-secondary">
                                    Attempt Time
                                </div>
                            </div>
                            <%!
                                // Helper method to get UserDAO from servlet context
                                UserDAO getUserDAO(ServletContext context) {
                                    return (UserDAO) context.getAttribute("UserDAO");
                                }

                                // Helper method to get a user by ID
                                User getUserById(UserDAO dao, long userId) throws SQLException {
                                    return dao.getUser(userId);
                                }

                                // Helper method to generate HTML for a quiz attempt row
                                void generateQuizAttemptRow(JspWriter out, User trier, quizzAttempt q) throws IOException {
                                    out.println("<div class='row mb-3'>");
                                    out.println("<div class='col'>");
                                    out.println("<a class='mb-0' href='UserServlet?username=" + trier.getUsername() + "'>" + trier.getUsername() + "</a>");
                                    out.println("</div>");
                                    out.println("<div class='col text-secondary'>" + q.getScore() + "</div>");
                                    out.println("<div class='col text-secondary'>" + q.getTimestamp() + "</div>");
                                    out.println("</div>");
                                }

                                // Helper method to iterate through the history and generate rows
                                void processQuizHistory(JspWriter out, List<quizzAttempt> history, UserDAO dao) throws IOException, SQLException {
                                    if (history != null) {
                                        for (quizzAttempt q : history) {
                                            long id = q.getUserId();
                                            User trier = getUserById(dao, id);
                                            generateQuizAttemptRow(out, trier, q);
                                        }
                                    }
                                }
                            %>

                            <%
                                List<quizzAttempt> history = quiz.getHistory();
                                UserDAO dao = getUserDAO(request.getServletContext());
                                processQuizHistory(out, history, dao);
                            %>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

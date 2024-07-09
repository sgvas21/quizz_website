<%@ page import="user.User" %>
<%@ page import="questions.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Recent Quizzes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%
    User currUser = (User) session.getAttribute("currUser");
    if (currUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Question> questionsList = (List<Question>)request.getAttribute("questionList");
    if(questionsList == null){
        response.sendRedirect("quizzes.jsp");
        return;
    }
%>

<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="card shadow-2-strong " style="border-radius: 1rem; width : 90% ;padding:5%">
            <form action='QuizTakeServlet' method='post'>
                <input type="hidden" name="quizId" value="<%=request.getParameter("quizId")%>">
                <%for (int i = 0; i < questionsList.size(); i++) {%>
                <label> <%= i + 1 %>) <%= questionsList.get(i).getQuestion() %></label>
                <% if (questionsList.get(i).getClass() == ResponseQuestion.class) {%>
                <input required type="text" name="<%= i %>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                <%} else if (questionsList.get(i).getClass() == Fill_InTheBlankQuestion.class) {%>
                <input required type="text" name="<%= i %>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                <%} else if (questionsList.get(i).getClass() == PictureResponseQuestion.class) {%>
                <input required type="text" name="<%= i %>" class="form-control form-control-lg mb-4" placeholder="Answer" />
                <%}%>
                <hr>
                <%}%>
                <input class="btn btn-success" type="submit" value="Submit">
            </form>
        </div>
    </div>
</div>

</body>
</html>
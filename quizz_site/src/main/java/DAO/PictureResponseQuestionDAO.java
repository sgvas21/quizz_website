package DAO;

import questions.PictureResponseQuestion;
import questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PictureResponseQuestionDAO implements QuestionDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public PictureResponseQuestionDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        if (!(question instanceof PictureResponseQuestion)) {
            throw new IllegalArgumentException("Invalid question type");
        }
        String sql = "INSERT INTO PictureResponseQuestion (question, picURL, correctAnswer, quiz_id) VALUES (?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        PictureResponseQuestion prq = (PictureResponseQuestion) question;
        statement.setString(1, prq.getQuestion());
        statement.setString(2, prq.getPicURL());
        statement.setString(3, prq.getCorrectAnswer());
        statement.setLong(4, quiz_id);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> listQuestion = new ArrayList<>();
        String sql = "SELECT * FROM PictureResponseQuestion WHERE quiz_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, quizId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String questionText = resultSet.getString("question");
            String picURL = resultSet.getString("picURL");
            String correctAnswer = resultSet.getString("correctAnswer");

            PictureResponseQuestion question = new PictureResponseQuestion();
            question.setQuestionId(id);
            question.setQuestion(questionText);
            question.setPicURL(picURL);
            question.setCorrectAnswer(correctAnswer);

            listQuestion.add(question);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return listQuestion;
    }
}

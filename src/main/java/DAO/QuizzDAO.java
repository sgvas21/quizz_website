package DAO;

import quizz.quizz;
import quizz.defaultQuizz;
import quizz.randomQuizz;
import quizz.quizzAttempt;
import questions.Question;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizzDAO {
    private Connection jdbcConnection;

    public QuizzDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    // Methods for handling quizzAttempt
    public void addQuizzAttempt(quizzAttempt attempt) throws SQLException {
        String sql = "INSERT INTO quizHistory (userId, score, attemptTime) VALUES (?, ?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, attempt.getUserId());
            statement.setDouble(2, attempt.getScore());
            statement.setTimestamp(3, attempt.getTimestamp());
            statement.executeUpdate();
        }
    }

    public List<quizzAttempt> getQuizzAttemptsByUser(long userId) throws SQLException {
        List<quizzAttempt> attempts = new ArrayList<>();
        String sql = "SELECT * FROM quizHistory WHERE userId = ?";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                double score = resultSet.getDouble("score");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                quizzAttempt attempt = new quizzAttempt(id, userId, score, timestamp);
                attempts.add(attempt);
            }
        }

        return attempts;
    }

    // Methods for handling quizz
    public void addQuizz(quizz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (quizName, author, type) VALUES (?, ?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, quiz.getName());
            statement.setLong(2, quiz.getAuthor().getId());
            statement.setString(3, quiz instanceof randomQuizz ? "random" : "default");
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                quiz.setId(generatedKeys.getLong(1));
            }
        }

        // Add questions and history
        addQuestions(quiz);
        addQuizzHistory(quiz);
    }

    public quizz getQuizzById(long id) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        quizz quiz = null;

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                long authorId = resultSet.getLong("authorId");
                String type = resultSet.getString("type");

                User author = getUserById(authorId); // Implement this method to get User object
                List<Question> questions = getQuestionsByQuizzId(id);
                List<quizzAttempt> history = getQuizzHistoryByQuizzId(id);

                if ("random".equals(type)) {
                    quiz = new randomQuizz(id, name, author, questions, history);
                } else {
                    quiz = new defaultQuizz(id, name, author, questions, history);
                }
            }
        }

        return quiz;
    }

    private void addQuestions(quizz quiz) throws SQLException {
        String sql = "INSERT INTO quizzQuestion (quizzId, questionId) VALUES (?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            for (Question question : quiz.getQuestions()) {
                statement.setLong(1, quiz.getId());
                statement.setLong(2, question.getQuestionId()); // Assuming Question class has getId() method
                statement.executeUpdate();
            }
        }
    }

    private List<Question> getQuestionsByQuizzId(long quizzId) throws SQLException {
        List<Question> allQuizzQuestions = new ArrayList<>();

        QuestionDAO responseQuestionDAO = new ResponseQuestionDAO(jdbcConnection);
        QuestionDAO fillInTheBlankQuestionDAO = new Fill_InTheBlankQuestionDAO(jdbcConnection);
        QuestionDAO multipleChoiceQuestionDAO = new MultipleChoiceQuestionDAO(jdbcConnection);
        QuestionDAO pictureResponseQuestionDAO = new PictureResponseQuestionDAO(jdbcConnection);

        allQuizzQuestions.addAll(responseQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(fillInTheBlankQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(multipleChoiceQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(pictureResponseQuestionDAO.getQuestions(quizzId));

        return allQuizzQuestions;
    }

    private void addQuizzHistory(quizz quiz) throws SQLException {
        for (quizzAttempt attempt : quiz.getHistory()) {
            addQuizzAttempt(attempt);
        }
    }

    private List<quizzAttempt> getQuizzHistoryByQuizzId(long quizzId) throws SQLException {
        List<quizzAttempt> attempts = new ArrayList<>();

        String query = "SELECT * FROM quizHistory WHERE quizId = ?";

        try {
            PreparedStatement statement = jdbcConnection.prepareStatement(query);
            statement.setLong(1, quizzId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                double score = resultSet.getDouble("score");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                quizzAttempt attempt = new quizzAttempt(id, quizzId, score, timestamp);
                attempts.add(attempt);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting quiz history: " + e.getMessage());
        }

        return attempts;
    }

    private User getUserById(long userId) throws SQLException {
        // Implement logic to retrieve a User object by userId
        return (new UserDAO(jdbcConnection)).getUser(userId); // Placeholder return
    }
}

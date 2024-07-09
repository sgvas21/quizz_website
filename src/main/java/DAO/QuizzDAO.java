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

    // Constructor to initialize the database connection
    public QuizzDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    // Method to add a quiz attempt to the database
    public void addQuizzAttempt(quizzAttempt attempt, long quizId) throws SQLException {
        String sql = "INSERT INTO quizHistory (quizId, userId, score, attemptTime) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, quizId);
            statement.setLong(2, attempt.getUserId());
            statement.setDouble(3, attempt.getScore());
            statement.setTimestamp(4, attempt.getTimestamp());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all quiz attempts by a specific user from the database
    public List<quizzAttempt> getQuizzAttemptsByUser(long userId) throws SQLException {
        List<quizzAttempt> attempts = new ArrayList<>();
        String sql = "SELECT * FROM quizHistory WHERE userId = ?";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                double score = resultSet.getDouble("score");
                Timestamp timestamp = resultSet.getTimestamp("attemptTime");

                quizzAttempt attempt = new quizzAttempt(id, userId, score, timestamp);
                attempts.add(attempt);
            }
        }

        return attempts;
    }

    // Method to add a quiz to the database
    public void addQuizz(quizz quiz) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO quizzes (author, quizName, type) VALUES (?, ?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, quiz.getAuthor().getId());
            statement.setString(2, quiz.getName());
            statement.setString(3, quiz instanceof randomQuizz ? "random" : "default");
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                quiz.setId(generatedKeys.getLong(1));
            }
        }

        // Add questions and history for the quiz
        addQuestions(quiz);
        addQuizzHistory(quiz);
    }

    // Method to remove a quiz from the database by its ID
    public void removeQuizById(long id) throws SQLException {
        PreparedStatement statement = jdbcConnection.prepareStatement("DELETE FROM quizzes WHERE id=?");
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    // Method to retrieve a quiz by its ID from the database
    public quizz getQuizzById(long id) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        quizz quiz = null;

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("quizName");
                long authorId = resultSet.getLong("author");
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

    // Method to add questions to a quiz
    private void addQuestions(quizz quiz) throws SQLException, ClassNotFoundException {
        List<Question> questions = quiz.getQuestions();
        for (Question question : questions) {
            question.getDao().addQuestion(question, quiz.getId());
        }
    }

    // Method to retrieve all questions for a specific quiz from the database
    private List<Question> getQuestionsByQuizzId(long quizzId) throws SQLException {
        List<Question> allQuizzQuestions = new ArrayList<>();

        // Instantiate DAOs for different question types
        QuestionDAO responseQuestionDAO = new ResponseQuestionDAO(jdbcConnection);
        QuestionDAO fillInTheBlankQuestionDAO = new Fill_InTheBlankQuestionDAO(jdbcConnection);
        QuestionDAO multipleChoiceQuestionDAO = new MultipleChoiceQuestionDAO(jdbcConnection);
        QuestionDAO pictureResponseQuestionDAO = new PictureResponseQuestionDAO(jdbcConnection);

        // Retrieve questions from each DAO and add to the list
        allQuizzQuestions.addAll(responseQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(fillInTheBlankQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(multipleChoiceQuestionDAO.getQuestions(quizzId));
        allQuizzQuestions.addAll(pictureResponseQuestionDAO.getQuestions(quizzId));

        return allQuizzQuestions;
    }

    // Method to add quiz history (attempts) to the database
    private void addQuizzHistory(quizz quiz) throws SQLException {
        for (quizzAttempt attempt : quiz.getHistory()) {
            addQuizzAttempt(attempt, quiz.getId());
        }
    }

    // Method to retrieve quiz history (attempts) for a specific quiz from the database
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
                Timestamp timestamp = resultSet.getTimestamp("attemptTime");

                quizzAttempt attempt = new quizzAttempt(id, quizzId, score, timestamp);
                attempts.add(attempt);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting quiz history: " + e.getMessage());
        }

        return attempts;
    }

    // Method to retrieve a user by their ID from the database
    private User getUserById(long userId) throws SQLException {
        // Implement logic to retrieve a User object by userId
        return (new UserDAO(jdbcConnection)).getUser(userId); // Placeholder return
    }

    // Method to retrieve all quizzes from the database
    public List<quizz> getQuizzes() throws SQLException {
        List<quizz> res = new ArrayList<>();
        String query = "SELECT * FROM quizzes ORDER BY id DESC";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                res.add(getQuizzById(rs.getLong("id")));
            }
        }
        return res;
    }
}

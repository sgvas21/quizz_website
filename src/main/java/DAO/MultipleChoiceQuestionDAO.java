package DAO;

import questions.MultipleChoiceQuestion;
import questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestionDAO implements QuestionDAO {
    private final Connection jdbcConnection;
    private final AnswerDAO answerDAO;

    // Constructor to initialize the database connection and AnswerDAO
    public MultipleChoiceQuestionDAO(Connection connection) {
        this.jdbcConnection = connection;
        this.answerDAO = new AnswerDAO(jdbcConnection);
    }

    // Method to add a multiple-choice question to the database
    @Override
    public void addQuestion(Question question, long quizId) throws SQLException {
        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;

        // Insert the multiple-choice question and get the generated question ID
        long questionId = insertMultipleChoiceQuestion(mcq, quizId);
        mcq.setQuestionId(questionId);

        // Insert the answers for the multiple-choice question
        insertMultipleChoiceQuestionAnswers(questionId, mcq.getCorrectAnswer(), mcq.getIncorrectAnswers());
    }

    // Method to insert a multiple-choice question into the database and return the generated question ID
    private long insertMultipleChoiceQuestion(MultipleChoiceQuestion mcq, long quizId) throws SQLException {
        String sql = "INSERT INTO MultipleChoiceQuestions (question, quizId) VALUES (?, ?)";
        PreparedStatement ps = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mcq.getQuestion());
        ps.setLong(2, quizId);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
            return rs.getLong(1);
        else
            throw new SQLException("Failed to retrieve the generated question ID");
    }

    // Method to insert the answers for a multiple-choice question into the database
    private void insertMultipleChoiceQuestionAnswers(long questionId, String correctAnswer, List<String> incorrectAnswers) throws SQLException {
        List<String> allAnswers = new ArrayList<>(incorrectAnswers);
        allAnswers.add(correctAnswer);

        for (int i = 0; i < allAnswers.size(); i++) {
            String answer = allAnswers.get(i);
            boolean isCorrectAnswer = answer.equals(correctAnswer);
            String sql = "INSERT INTO MultipleChoiceQuestionsAnswers (answer, questionId, is_correct_answer) VALUES (?, ?, ?)";
            PreparedStatement ps = jdbcConnection.prepareStatement(sql);
            ps.setString(1, answer);
            ps.setLong(2, questionId);
            ps.setBoolean(3, isCorrectAnswer);
            ps.executeUpdate();
        }
    }

    // Method to retrieve all multiple-choice questions for a specific quiz from the database
    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();

        String sql = "SELECT * FROM MultipleChoiceQuestions WHERE quizId = ?";
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setLong(1, quizId);
        ResultSet rs = ps.executeQuery();

        // Loop through the result set and create MultipleChoiceQuestion objects
        while (rs.next()) {
            int id = rs.getInt(1);
            String question = rs.getString("question");
            String correctAnswer = getAnswersFromDB(id, true).get(0);
            List<String> incorrectAnswers = getAnswersFromDB(id, false);

            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(id, question, correctAnswer, incorrectAnswers);
            result.add(mcq);
        }

        return result;
    }

    // Method to retrieve answers for a specific question from the database
    private List<String> getAnswersFromDB(int questionId, boolean isCorrectAnswer) throws SQLException {
        StringBuffer sql = new StringBuffer("SELECT * FROM MultipleChoiceQuestionsAnswers WHERE questionId = ? AND is_correct_answer = ");
        if (isCorrectAnswer)
            sql.append("1");
        else
            sql.append("0");

        return answerDAO.getAnswers(questionId, sql.toString());
    }
}

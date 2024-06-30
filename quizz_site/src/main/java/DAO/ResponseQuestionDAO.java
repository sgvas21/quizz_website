package DAO;

import questions.Question;
import questions.ResponseQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseQuestionDAO implements QuestionDAO {
    private final Connection con;
    private final AnswerDAO ad;

    public ResponseQuestionDAO(Connection con) {
        this.con = con;
        this.ad = new AnswerDAO(con);
    }

    /**
     * Adds a ResponseQuestion to the database, along with its associated answers.
     *
     * @param question The ResponseQuestion to be added.
     * @param quizId The ID of the quiz to which this question belongs.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    @Override
    public void addQuestion(Question question, long quizId) throws SQLException {
        ResponseQuestion rq = (ResponseQuestion) question;
        long questionId = insertResponseQuestion(rq, quizId);
        insertResponseQuestionAnswers(rq.getLegalAnswers(), questionId);
    }

    /**
     * Inserts a ResponseQuestion into the database.
     *
     * @param rq The ResponseQuestion to be inserted.
     * @param quizId The ID of the quiz to which this question belongs.
     * @return The generated question ID.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    private long insertResponseQuestion(ResponseQuestion rq, long quizId) throws SQLException {
        String sql = "INSERT INTO responsequestions(question, quizId) VALUES (?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, rq.getQuestion());
            statement.setLong(2, quizId);
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve the generated question ID.");
                }
            }
        }
    }

    /**
     * Inserts the answers for a ResponseQuestion into the database.
     *
     * @param answers The list of answers to be inserted.
     * @param questionId The ID of the question to which these answers belong.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    private void insertResponseQuestionAnswers(List<String> answers, long questionId) throws SQLException {
        String sql = "INSERT INTO responsequestionsanswer(answer, questionId) VALUES (?, ?)";
        ad.insertAnswers(sql, questionId, answers);
    }

    /**
     * Retrieves all ResponseQuestion objects for a specific quiz from the database.
     *
     * @param quizId The ID of the quiz for which to retrieve questions.
     * @return A list of Question objects corresponding to the specified quiz.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<ResponseQuestion> questions = fetchResponseQuestions(quizId);
        for (ResponseQuestion rq : questions) {
            List<String> answers = fetchAnswersForQuestion(rq.getQuestionId());
            rq.setLegalAnswers(answers);
            result.add(rq);
        }
        return result;
    }

    /**
     * Fetches the ResponseQuestion objects for a specific quiz from the database.
     *
     * @param quizId The ID of the quiz for which to fetch questions.
     * @return A list of ResponseQuestion objects.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    private List<ResponseQuestion> fetchResponseQuestions(long quizId) throws SQLException {
        String sql = "SELECT * FROM responsequestions WHERE quizId = ?";
        List<ResponseQuestion> questions = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, quizId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ResponseQuestion rq = new ResponseQuestion();
                    rq.setQuestion(rs.getString("question"));
                    rq.setQuestionId(rs.getInt("id"));
                    questions.add(rq);
                }
            }
        }
        return questions;
    }

    /**
     * Fetches the answers for a specific question from the database.
     *
     * @param questionId The ID of the question for which to fetch answers.
     * @return A list of answers.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    private List<String> fetchAnswersForQuestion(long questionId) throws SQLException {
        String sql = "SELECT * FROM responsequestionsanswer WHERE questionId = ?";
        return ad.getAnswers(questionId, sql);
    }
}

package DAO;

import questions.Fill_InTheBlankQuestion;
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
        String sql = "INSERT INTO responsequestionsanswers(answer, questionId) VALUES (?, ?)";
        ad.insertAnswers(sql, questionId, answers);
    }

    /**
     * This method retrieves all questions for a given quizId.
     * It fetches Response-Question questions and adds them to the result list.
     *
     * @param quizId The ID of the quiz for which questions are to be retrieved.
     * @return A list of questions for the specified quiz.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<ResponseQuestion> res = getQuestionsResponseQuestions(quizId);
        result.addAll(res);
        return result;
    }

    /**
     * This method retrieves all Response-Question questions for a given quizId.
     * It executes a SQL query to fetch the questions and maps the result set to a list of Response-Question objects.
     *
     * @param quizId The ID of the quiz for which Response-Question questions are to be retrieved.
     * @return A list of Response-Question questions for the specified quiz.
     */
    private List<ResponseQuestion> getQuestionsResponseQuestions(long quizId) {
        List<ResponseQuestion> result = new ArrayList<>();

        try (PreparedStatement st = prepareResponseQuestionStatement(quizId);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToResponseQuestion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * This method prepares a SQL statement for retrieving Response-Question questions based on the quizId.
     *
     * @param quizId The ID of the quiz for which the SQL statement is to be prepared.
     * @return A prepared statement for retrieving Response-Question questions.
     * @throws SQLException If an SQL error occurs during the statement preparation.
     */
    private PreparedStatement prepareResponseQuestionStatement(long quizId) throws SQLException {
        PreparedStatement st = con.prepareStatement("SELECT * FROM responsequestions WHERE quizId=?");
        st.setLong(1, quizId);
        return st;
    }

    /**
     * This method maps a row from the result set to a Response-Question object.
     *
     * @param rs The result set containing the query results.
     * @return A Response-Question object mapped from the current row of the result set.
     * @throws SQLException If an SQL error occurs during the mapping process.
     */
    private ResponseQuestion mapRowToResponseQuestion(ResultSet rs) throws SQLException {
        String question = rs.getString("question");
        long questionId = rs.getLong("id");
        String statement = "SELECT * FROM ResponseQuestionsAnswers WHERE questionId = ?;";
        List<String> legalAnswers = ad.getAnswers(questionId, statement);
        return new ResponseQuestion(question, legalAnswers, (int) questionId);
    }
}

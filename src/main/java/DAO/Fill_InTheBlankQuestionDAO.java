package DAO;

import questions.Fill_InTheBlankQuestion;
import questions.Question;
import questions.ResponseQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Fill_InTheBlankQuestionDAO implements QuestionDAO{
    private final Connection con;
    private final AnswerDAO ad;

    public Fill_InTheBlankQuestionDAO(Connection con) {
        this.con = con;
        this.ad = new AnswerDAO(con);
    }

    /**
     * Adds a Fill_InTheBlankQuestion to the database.
     * @param question The Fill_InTheBlankQuestion object to add.
     * @param quiz_id The ID of the quiz to which the question belongs.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        Fill_InTheBlankQuestion fq = (Fill_InTheBlankQuestion) question;
        long questionId = insertFill_InTheBlankQuestion(fq, quiz_id);
        insertFill_InTheBlankQuestionAnswers(fq.getLegalAnswers(), questionId);
    }

    /**
     * Inserts a Fill_InTheBlankQuestion into the database.
     * @param fq The Fill_InTheBlankQuestion object to insert.
     * @param quizId The ID of the quiz to which the question belongs.
     * @return The generated question ID.
     * @throws SQLException If a database error occurs.
     */
    private long insertFill_InTheBlankQuestion(Fill_InTheBlankQuestion fq, long quizId) throws SQLException {
        String sql = "INSERT INTO fillintheblankquestions(question, quizId) VALUES (?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fq.getQuestion());
            statement.setLong(2, quizId);
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("Failed to retrieve the generated question ID.");
            }
        }
    }

    /**
     * Inserts answers for a Fill_InTheBlankQuestion into the database.
     * @param answers The list of answers to insert.
     * @param questionId The ID of the question to associate answers with.
     * @throws SQLException If a database error occurs.
     */
    private void insertFill_InTheBlankQuestionAnswers(List<String> answers, long questionId) throws SQLException {
        String sql = "INSERT INTO FillInTheBlankQuestionsAnswers(answer, questionId) VALUES (?, ?)";
        ad.insertAnswers(sql, questionId, answers);
    }

    /**
     * This method retrieves all questions for a given quizId.
     * It fetches fill-in-the-blank questions and adds them to the result list.
     *
     * @param quizId The ID of the quiz for which questions are to be retrieved.
     * @return A list of questions for the specified quiz.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<Fill_InTheBlankQuestion> res = getQuestionsFillInTheBlank(quizId);
        result.addAll(res);
        return result;
    }

    /**
     * This method retrieves all fill-in-the-blank questions for a given quizId.
     * It executes a SQL query to fetch the questions and maps the result set to a list of Fill_InTheBlankQuestion objects.
     *
     * @param quizId The ID of the quiz for which fill-in-the-blank questions are to be retrieved.
     * @return A list of fill-in-the-blank questions for the specified quiz.
     */
    private List<Fill_InTheBlankQuestion> getQuestionsFillInTheBlank(long quizId) {
        List<Fill_InTheBlankQuestion> result = new ArrayList<>();

        try (PreparedStatement st = prepareFillInTheBlankStatement(quizId);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToFillInTheBlankQuestion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * This method prepares a SQL statement for retrieving fill-in-the-blank questions based on the quizId.
     *
     * @param quizId The ID of the quiz for which the SQL statement is to be prepared.
     * @return A prepared statement for retrieving fill-in-the-blank questions.
     * @throws SQLException If an SQL error occurs during the statement preparation.
     */
    private PreparedStatement prepareFillInTheBlankStatement(long quizId) throws SQLException {
        PreparedStatement st = con.prepareStatement("SELECT * FROM fillintheblankquestions WHERE quizId=?");
        st.setLong(1, quizId);
        return st;
    }

    /**
     * This method maps a row from the result set to a Fill_InTheBlankQuestion object.
     *
     * @param rs The result set containing the query results.
     * @return A Fill_InTheBlankQuestion object mapped from the current row of the result set.
     * @throws SQLException If an SQL error occurs during the mapping process.
     */
    private Fill_InTheBlankQuestion mapRowToFillInTheBlankQuestion(ResultSet rs) throws SQLException {
        String question = rs.getString("question");
        long questionId = rs.getLong("id");
        String statement = "SELECT * FROM FIllInTheBlankQuestionsAnswers WHERE questionId = ?;";
        List<String> legalAnswers = ad.getAnswers(questionId, statement);
        return new Fill_InTheBlankQuestion(question, legalAnswers, (int) questionId);
    }

}

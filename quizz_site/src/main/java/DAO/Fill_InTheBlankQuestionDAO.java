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
        String sql = "INSERT INTO FillInTheBlankAnswer(answer, questionId) VALUES (?, ?)";
        ad.insertAnswers(sql, questionId, answers);
    }

    /**
     * Retrieves all Fill_InTheBlankQuestion objects for a given quiz ID.
     * @param quizId The ID of the quiz.
     * @return A list of Fill_InTheBlankQuestion objects.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<Fill_InTheBlankQuestion> questions = fetchFillInTheBlankQuestions(quizId);
        for (Fill_InTheBlankQuestion fq : questions) {
            List<String> answers = fetchAnswersForQuestion(fq.getQuestionId());
            fq.setLegalAnswers(answers);
            result.add(fq);
        }
        return result;
    }

    /**
     * Fetches all Fill_InTheBlankQuestion objects for a given quiz ID from the database.
     * @param quizId The ID of the quiz.
     * @return A list of Fill_InTheBlankQuestion objects.
     * @throws SQLException If a database error occurs.
     */
    private List<Fill_InTheBlankQuestion> fetchFillInTheBlankQuestions(long quizId) throws SQLException {
        String sql = "SELECT * FROM FillInTheBlankQuestions WHERE quizId = ?";
        List<Fill_InTheBlankQuestion> questions = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(sql)){
            statement.setLong(1, quizId);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    Fill_InTheBlankQuestion fq = new Fill_InTheBlankQuestion();
                    fq.setQuestion(rs.getString("question"));
                    fq.setQuestionId(rs.getInt("id"));
                    questions.add(fq);
                }
            }
        }
        return questions;
    }

    /**
     * Fetches answers for a specific Fill_InTheBlankQuestion from the database.
     * @param questionId The ID of the question.
     * @return A list of answers associated with the question.
     * @throws SQLException If a database error occurs.
     */
    private List<String> fetchAnswersForQuestion(int questionId) throws SQLException {
        String sql = "SELECT * FROM FillInTheBlankAnswer WHERE questionId = ?";
        return ad.getAnswers(questionId, sql);
    }
}

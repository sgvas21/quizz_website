package DAO;

import questions.MultipleChoiceQuestion;
import questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestionDAO implements QuestionDAO {
    private final Connection jdbcConnection;
    private final AnswerDAO answerDAO;

    MultipleChoiceQuestionDAO(Connection connection) {
        this.jdbcConnection = connection;
        this.answerDAO = new AnswerDAO(jdbcConnection);
    }

    @Override
    public void addQuestion(Question question, long quizId) throws SQLException {
//        if(!(question instanceof MultipleChoiceQuestion)) {
//            throw new IllegalArgumentException("Invalid question type");
//        }

        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;

        long questionId = insertMultipleChoiceQuestion(mcq, quizId);
        mcq.setQuestionId(questionId);
        insertMultipleChoiceQuestionAnswers(questionId, mcq.getCorrectAnswer(), mcq.getIncorrectAnswers());
    }

    private long insertMultipleChoiceQuestion(MultipleChoiceQuestion mcq, long quizId) throws SQLException {
        String sql = "INSERT INTO MultipleChoiceQuestions (question, quiz_id) VALUES (?, ?)";
        PreparedStatement ps = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mcq.getQuestion());
        ps.setLong(2, quizId);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            return rs.getLong(1);
        else throw new SQLException("Failed to retrieve the generated question ID");

    }

    private void insertMultipleChoiceQuestionAnswers(long questionId, String correctAnswer, List<String> incorrectAnswers) throws SQLException {
        List<String> allAnswers = new ArrayList<>(incorrectAnswers);
        allAnswers.add(correctAnswer);
    
        for (int i = 0; i < allAnswers.size(); i++) {
            String answer = allAnswers.get(i);
            boolean isCorrectAnswer = answer.equals(correctAnswer);
            String sql = "INSERT INTO MultipleChoiceQuestionAnswers (question_id, answer, is_correct_answer) VALUES (?, ?, ?)";
            PreparedStatement ps = jdbcConnection.prepareStatement(sql);
            ps.setLong(1, questionId);
            ps.setString(2, answer);
            ps.setBoolean(3, isCorrectAnswer);
            ps.executeUpdate();
        }
    }


    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();

        String sql = "SELECT * FROM MultipleChoiceQuestions WHERE quiz_id = ?";
        PreparedStatement ps = jdbcConnection.prepareStatement(sql);
        ps.setLong(1, quizId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt(1);
            String question = rs.getString("question");
            String correctAnswer = getAnswersFromDB(id, true).getFirst();
            List<String> incorrectAnswers = getAnswersFromDB(id, false);

            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(id, question, correctAnswer, incorrectAnswers);
            result.add(mcq);
        }

        return result;
    }

    private List<String> getAnswersFromDB(int questionId, boolean isCorrectAnswer) throws SQLException {
        StringBuffer sql = new StringBuffer("SELECT * FROM MultipleChoiceQuestionAnswers WHERE question_id = ? AND is_correct_answer = ");
        if(isCorrectAnswer)
            sql.append("1");
        else
            sql.append("0");

        return answerDAO.getAnswers(questionId, sql.toString());
    }
}

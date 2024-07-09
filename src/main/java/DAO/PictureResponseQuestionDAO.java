package DAO;

import questions.PictureResponseQuestion;
import questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PictureResponseQuestionDAO implements QuestionDAO {
    private final Connection jdbcConnection;
    private final AnswerDAO ad;

    public PictureResponseQuestionDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        this.ad = new AnswerDAO(jdbcConnection);
    }

    @Override
    public void addQuestion(Question question, long quiz_id) throws SQLException {
        if (!(question instanceof PictureResponseQuestion)) {
            throw new IllegalArgumentException("Invalid question type");
        }
        PictureResponseQuestion prq = (PictureResponseQuestion) question;
        PreparedStatement statement = jdbcConnection.prepareStatement(
                "INSERT INTO PictureResponseQuestions (question, img_url, quizId) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, prq.getQuestion());
        statement.setString(2, prq.getPicURL());
        statement.setLong(3, quiz_id);
        statement.execute();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long questionId = rs.getLong(1);

        List<String> answers = prq.getLegalAnswers();
        String sql = "INSERT INTO PictureResponseQuestionsAnswers(answer, questionId) VALUES(?, ?);";
        ad.insertAnswers(sql, questionId, answers);
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> listQuestion = new ArrayList<>();
        String sql = "SELECT * FROM PictureResponseQuestions WHERE quizId = ?";

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, quizId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long questionId = resultSet.getLong("id");
            String questionText = resultSet.getString("question");
            String picURL = resultSet.getString("img_url");

            String s = "SELECT * FROM PictureResponseQuestionsAnswers WHERE questionId = ?;";
            List<String> legalAnswers = ad.getAnswers(questionId, s);
            PictureResponseQuestion pq = new PictureResponseQuestion(questionText, legalAnswers, picURL);
            listQuestion.add(pq);
        }

        resultSet.close();
        statement.close();

        return listQuestion;
    }
}

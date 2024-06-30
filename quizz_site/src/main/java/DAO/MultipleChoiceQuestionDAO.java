package DAO;

import questions.MultipleChoiceQuestion;
import questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestionDAO implements QuestionDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    private static final String INCORRECT_ANSWER_NAME = "incorrectAnswer";

    MultipleChoiceQuestionDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
        if(!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("Invalid question type");
        }

        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
        int numIncorrectAnswers = mcq.getNumIncorrectAnswers();

        String sql = "INSERT INTO MultipleChoiceQuestions (quiz_id, question, numIncorrectAnswers, correctAnswer" +
                     getincorrectAnswerAliases(numIncorrectAnswers) +
                     ") VALUES (?, ?, ?, ?" +
                     getValueQMarks(numIncorrectAnswers) +
                     ")";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        int parameterIndex = 1;
        statement.setLong(parameterIndex++, quiz_id);
        statement.setString(parameterIndex++, mcq.getQuestion());
        statement.setInt(parameterIndex++, mcq.getNumIncorrectAnswers());
        statement.setString(parameterIndex++, mcq.getCorrectAnswer());
        int startIndex = parameterIndex;
        while(parameterIndex < startIndex + numIncorrectAnswers) {
            statement.setString(parameterIndex, mcq.getIncorrectAnswers().get(parameterIndex - startIndex));
            parameterIndex++;
        }

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    private StringBuffer getincorrectAnswerAliases(int numIncorrectAnswers) {
        StringBuffer incorrectAnswerAliases = new StringBuffer();
        for (int i = 1; i <= numIncorrectAnswers; i++) {
            incorrectAnswerAliases.append(", ").append(INCORRECT_ANSWER_NAME).append(i);
        }
        return incorrectAnswerAliases;
    }

    private StringBuffer getValueQMarks(int numIncorrectAnswers) {
        StringBuffer valueQMarks = new StringBuffer();
        for (int i = 1; i <= numIncorrectAnswers; i++) {
            valueQMarks.append(", ?");
        }
        return valueQMarks;
    }

    @Override
    public List<Question> getQuestions(long quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        String query = "SELECT * FROM MultipleChoiceQuestions WHERE quiz_id = ?";

        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(query);
        statement.setLong(1, quizId);
        ResultSet queryResultSet = statement.executeQuery();

        while (queryResultSet.next()) {
            int id = queryResultSet.getInt("id");
            String question = queryResultSet.getString("question");
            String correctAnswer = queryResultSet.getString("correctAnswer");

            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
            mcq.setQuestionId(id);
            mcq.setQuizId(quizId);
            mcq.setQuestion(question);
            mcq.setCorrectAnswer(correctAnswer);
            mcq.setIncorrectAnswers(getIncorrectAnswersFromQuery(queryResultSet));

            result.add(mcq);
        }

        disconnect();

        return result;
    }

    private List<String> getIncorrectAnswersFromQuery(ResultSet queryResultSet) throws SQLException {
        List<String> result = new ArrayList<>();

        int numIncorrectAnswers = queryResultSet.getInt("numIncorrectAnswers");
        for (int i = 1; i <= numIncorrectAnswers; i++) {
            String incorrectAnswerAlias = "incorrectAnswer" + i;
            result.add(queryResultSet.getString(incorrectAnswerAlias));
        }

        return result;
    }
}

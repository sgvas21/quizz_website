package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private final Connection con;

    public AnswerDAO(Connection con) { this.con = con; }

    /**
     * Inserts a list of answers into the database for a given question.
     *
     * @param statement The SQL statement to be executed, typically an INSERT statement.
     *                  It should have two placeholders for the answer text and question ID.
     * @param questionId The ID of the question to which these answers belong.
     * @param answers The list of answers to be inserted into the database.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    public void insertAnswers(String statement, long questionId, List<String> answers) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(statement)) {
            for (String answer : answers) {
                st.setString(1, answer);
                st.setLong(2, questionId);
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    /**
     * Retrieves a list of answers from the database for a specific question ID.
     *
     * @param questionId The ID of the question for which to retrieve answers.
     * @param statement The SQL statement to be executed, typically a SELECT statement.
     *                  It should have one placeholder for the question ID.
     * @return A list of answers corresponding to the specified question ID.
     * @throws SQLException If there is any issue with database access or the SQL statement.
     */
    public List<String> getAnswers(long questionId, String statement) throws SQLException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(statement)) {
            st.setLong(1, questionId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("answer"));
                }
            }
        }
        return result;
    }
}

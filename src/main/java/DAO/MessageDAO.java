package DAO;

import mails.message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection jdbcConnection;

    // Constructor that initializes the database connection
    public MessageDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    // Method to add a message to the database
    public void addMessage(message msg) throws SQLException {
        // SQL query to insert a new message record into the 'messages' table
        String sql = "INSERT INTO messages (fromId, toId, message, sentTime) VALUES (?, ?, ?, ?)";

        // Prepared statement to safely set parameters and execute the query
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, msg.getFromId());
        statement.setLong(2, msg.getToId());
        statement.setString(3, msg.getMessage());
        statement.setTimestamp(4, msg.getSentTime());

        // Execute the query to insert the message
        statement.executeUpdate();
        statement.close();
    }

    // Method to retrieve all messages sent to or received by a specific user
    public List<message> getMessagesByUser(long userId) throws SQLException {
        List<message> listMessages = new ArrayList<>();
        // SQL query to select messages where the user is either the sender or the receiver
        String sql = "SELECT * FROM messages WHERE fromId = ? OR toId = ?";

        // Prepared statement to safely set parameters and execute the query
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        ResultSet resultSet = statement.executeQuery();

        // Loop through the result set and create message objects
        while (resultSet.next()) {
            long messageId = resultSet.getLong("id");
            long fromId = resultSet.getLong("fromId");
            long toId = resultSet.getLong("toId");
            String messageText = resultSet.getString("message");
            Timestamp sentTime = resultSet.getTimestamp("sentTime");

            message msg = new message(messageId, fromId, toId, messageText, sentTime);
            listMessages.add(msg);
        }

        resultSet.close();
        statement.close();

        return listMessages;
    }

    // Method to retrieve messages exchanged between two users
    public List<message> getMessages(long firstId, long secondId) throws SQLException {
        List<message> result = new ArrayList<>();
        // SQL query to select messages exchanged between the two users, ordered by sent time
        PreparedStatement statement = jdbcConnection.prepareStatement(
                "SELECT * FROM messages WHERE ((fromId=? AND toId=?) OR (fromId=? AND toId=?)) ORDER BY sentTime");
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        statement.setLong(3, secondId);
        statement.setLong(4, firstId);
        ResultSet res = statement.executeQuery();

        // Loop through the result set and create message objects
        while (res.next()) {
            String msg = res.getString("message");
            long msgId = res.getLong("id");
            long toId = res.getLong("toId");
            long fromId = res.getLong("fromId");
            Timestamp sentTime = res.getTimestamp("sentTime");
            message curr = new message(msgId, fromId, toId, msg, sentTime);
            result.add(curr);
        }

        // Close the resources
        res.close();
        statement.close();

        return result;
    }

}

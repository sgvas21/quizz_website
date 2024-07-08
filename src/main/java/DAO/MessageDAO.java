package DAO;

import mails.message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection jdbcConnection;

    public MessageDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public void addMessage(message msg) throws SQLException {
        String sql = "INSERT INTO messages (fromId, toId, message, sentTime) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, msg.getFromId());
        statement.setLong(2, msg.getToId());
        statement.setString(3, msg.getMessage());
        statement.setTimestamp(4, msg.getSentTime());

        statement.executeUpdate();
        statement.close();
    }

    public List<message> getMessagesByUser(long userId) throws SQLException {
        List<message> listMessages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE fromId = ? OR toId = ?";

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        ResultSet resultSet = statement.executeQuery();

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

    public List<message> getMessages(long firstId, long secondId) throws SQLException {
        List<message> result = new ArrayList<>();
        PreparedStatement statement = jdbcConnection.prepareStatement
                ("SELECT * FROM messages WHERE ((fromId=? AND toId=?) OR (fromId=? AND toId=?)) ORDER BY sentTime");
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        statement.setLong(3, secondId);
        statement.setLong(4, firstId);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            String msg = res.getString("message");
            long msgId = res.getLong("id");
            long toId = res.getLong("toId");
            long fromId = res.getLong("fromId");
            Timestamp sentTime = res.getTimestamp("sentTime");
            message curr = new message(msgId, fromId, toId, msg, sentTime);
            result.add(curr);
        }
        return result;
    }

}
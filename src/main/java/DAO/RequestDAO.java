package DAO;

import mails.request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private Connection jdbcConnection;

    // Constructor to initialize the database connection
    public RequestDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    // Method to add a request to the database
    public void addRequest(request req) throws SQLException {
        String sql = "INSERT INTO requests (fromId, toId) VALUES (?, ?)";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, req.getFromId());
            statement.setLong(2, req.getToId());
            statement.executeUpdate();
        }
    }

    // Method to remove a request from the database
    public void removeRequest(request req) throws SQLException {
        String sql = "DELETE FROM requests WHERE fromId=? AND toId=?";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, req.getFromId());
            statement.setLong(2, req.getToId());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all requests for a specific user from the database
    public List<request> getRequestsByUser(long userId) throws SQLException {
        List<request> listRequests = new ArrayList<>();
        String sql = "SELECT * FROM requests WHERE fromId = ? OR toId = ?";

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long requestId = resultSet.getLong("id");
                long fromId = resultSet.getLong("fromId");
                long toId = resultSet.getLong("toId");

                request req = new request(requestId, fromId, toId);
                listRequests.add(req);
            }
        }

        return listRequests;
    }
}

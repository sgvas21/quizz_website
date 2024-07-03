package DAO;

import mails.request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private Connection jdbcConnection;

    public RequestDAO(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public void addRequest(request req) throws SQLException {
        String sql = "INSERT INTO request (fromId, toId) VALUES (?, ?)";

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, req.getFromId());
        statement.setLong(2, req.getToId());

        statement.executeUpdate();
        statement.close();
    }

    public List<request> getRequestsByUser(long userId) throws SQLException {
        List<request> listRequests = new ArrayList<>();
        String sql = "SELECT * FROM request WHERE fromId = ? OR toId = ?";

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long requestId = resultSet.getLong("requestId");
            long fromId = resultSet.getLong("fromId");
            long toId = resultSet.getLong("toId");

            request req = new request(requestId, fromId, toId);
            listRequests.add(req);
        }

        resultSet.close();
        statement.close();

        return listRequests;
    }
}

package DAO;

import mails.request;
import user.User;
import user.Hash;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.DBConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestDAOTest {
    private static User testUser;
    private static User friendUser;
    private static UserDAO userDao;
    private static RequestDAO requestDao;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        testUser = new User("username", new Hash("pass").hashingPassword(), "firstname", "lastname", true);
        friendUser = new User("friendUsername", new Hash("password").hashingPassword(), "friendFirstname", "friendLastname", true);

        userDao = new UserDAO(DBConnection.getConnection());
        requestDao = new RequestDAO(DBConnection.getConnection());

        userDao.createUser(testUser);
        userDao.createUser(friendUser);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException {
        DBConnection.resetTables();
    }

    @Test
    public void testAddAndRetrieveRequest() throws SQLException, IOException, ClassNotFoundException {
        request req = new request(testUser.getId(), friendUser.getId());
        requestDao.addRequest(req);

        List<request> requests = requestDao.getRequestsByUser(testUser.getId());
        assertFalse(requests.isEmpty());
        assertEquals(friendUser.getId(), requests.get(0).getToId());

        PreparedStatement st = DBConnection.getConnection().prepareStatement
                ("DELETE FROM requests WHERE fromId =?");
        st.setLong(1, req.getFromId());
        st.execute();
    }

    @Test
    public void testRetrieveRequestsForUser() throws SQLException {
        request req1 = new request(testUser.getId(), friendUser.getId());
        request req2 = new request(friendUser.getId(), testUser.getId());
        requestDao.addRequest(req1);
        requestDao.addRequest(req2);

        List<request> requestsForTestUser = requestDao.getRequestsByUser(testUser.getId());
        List<request> requestsForFriendUser = requestDao.getRequestsByUser(friendUser.getId());

        assertEquals(2, requestsForTestUser.size());
        assertEquals(2, requestsForFriendUser.size());
    }
}

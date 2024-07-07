package DAO;

import mails.message;
import user.User;
import user.Hash;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.DBConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageDAOTest {
    private static User testUser;
    private static User receiverUser;
    private static UserDAO userDao;
    private static MessageDAO messageDao;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        testUser = new User("username", new Hash("pass").hashingPassword(), "firstname", "lastname", true);
        receiverUser = new User("receiverUsername", new Hash("password").hashingPassword(), "receiverFirstname", "receiverLastname", true);

        userDao = new UserDAO(DBConnection.getConnection());
        messageDao = new MessageDAO(DBConnection.getConnection());

        userDao.createUser(testUser);
        userDao.createUser(receiverUser);
    }

    @AfterAll
    public static void tearDown() throws SQLException, IOException, ClassNotFoundException {
        DBConnection.resetTables();
    }

    @Test
    public void testAddAndRetrieveMessage() throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message msg = new message(testUser.getId(), receiverUser.getId(), "Hello", timestamp);
        messageDao.addMessage(msg);

        List<message> messages = messageDao.getMessagesByUser(testUser.getId());
        assertFalse(messages.isEmpty());
        assertEquals("Hello", messages.get(0).getMessage());
    }

    @Test
    public void testRetrieveMessagesForUser() throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message msg1 = new message(testUser.getId(), receiverUser.getId(), "Hello", timestamp);
        message msg2 = new message(receiverUser.getId(), testUser.getId(), "Hi", timestamp);
        messageDao.addMessage(msg1);
        messageDao.addMessage(msg2);

        List<message> messagesForTestUser = messageDao.getMessagesByUser(testUser.getId());
        List<message> messagesForReceiverUser = messageDao.getMessagesByUser(receiverUser.getId());

        assertEquals(2, messagesForTestUser.size());
        assertEquals(2, messagesForReceiverUser.size());
    }
}

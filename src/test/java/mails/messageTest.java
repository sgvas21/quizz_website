package mails;

import static org.junit.jupiter.api.Assertions.*;

import DAO.MessageDAO;
import DAO.UserDAO;
import database.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.Hash;
import user.User;

import java.sql.SQLException;
import java.sql.Timestamp;

public class messageTest {
    private message msg1;
    private message msg2;
    private Timestamp timestamp;

    @BeforeEach
    public void setUp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        msg1 = new message(1L, 2L, 3L, "Hello", timestamp);
        msg2 = new message(4L, 5L, "Hi", timestamp);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, msg1.getMessageId());
        assertEquals(2L, msg1.getFromId());
        assertEquals(3L, msg1.getToId());
        assertEquals("Hello", msg1.getMessage());
        assertEquals(timestamp, msg1.getSentTime());

        assertEquals(4L, msg2.getFromId());
        assertEquals(5L, msg2.getToId());
        assertEquals("Hi", msg2.getMessage());
        assertEquals(timestamp, msg2.getSentTime());
    }

    @Test
    public void testSetters() {
        msg1.setMessageId(10L);
        msg1.setFromId(20L);
        msg1.setToId(30L);
        msg1.setMessage("Updated Message");

        assertEquals(10L, msg1.getMessageId());
        assertEquals(20L, msg1.getFromId());
        assertEquals(30L, msg1.getToId());
        assertEquals("Updated Message", msg1.getMessage());
    }

    @Test
    public void testToString() {
        assertEquals("Message{messageId=1, fromId=2, toId=3, Message='Hello'}", msg1.toString());
    }
}

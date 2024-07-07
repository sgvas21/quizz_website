package mails;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class requestTest {
    private request req1;
    private request req2;

    @BeforeEach
    public void setUp() {
        req1 = new request(1L, 2L, 3L);
        req2 = new request(4L, 5L);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, req1.getRequestId());
        assertEquals(2L, req1.getFromId());
        assertEquals(3L, req1.getToId());
        assertEquals(4L, req2.getFromId());
        assertEquals(5L, req2.getToId());
    }

    @Test
    public void testSetters() {
        req1.setRequestId(10L);
        req1.setFromId(20L);
        req1.setToId(30L);

        assertEquals(10L, req1.getRequestId());
        assertEquals(20L, req1.getFromId());
        assertEquals(30L, req1.getToId());
    }

    @Test
    public void testToString() {
        assertEquals("FriendRequest{fromId=2, toId=3, requestId=1}", req1.toString());
    }
}

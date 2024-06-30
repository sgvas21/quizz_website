package user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashTest {

    @Test
    public void testHash_common() {
        String commonPassword = "molly";

        Hash hash = new Hash(commonPassword);
        Hash hash2 = new Hash(commonPassword);

        assertNotEquals(commonPassword, hash.hashingPassword());
        assertEquals(hash.hashingPassword(), hash2.hashingPassword());
    }

}

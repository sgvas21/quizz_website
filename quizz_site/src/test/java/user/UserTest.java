package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private final int ADMIN_ID = -1;
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";
    private final String ADMIN_FIRSTNAME = "adMIN";

    private final int NONADMIN_ID = -2;
    private final String NONADMIN_USERNAME = "NOadmin";
    private final String NONADMIN_PASSWORD = "NOadmin123";
    private final String NONADMIN_FIRSTNAME = "not";
    private final String NONADMIN_SURNAME = "admin";


    private User userEmpty, userAdmin, userNonAdmin, userNonAdminCopy;

    @BeforeEach
    public void setUp() {
        userEmpty = new User();

        //With ID
        userAdmin = new User(ADMIN_ID, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_FIRSTNAME, "", true);

        //Without ID
        userNonAdmin = new User(NONADMIN_USERNAME, NONADMIN_PASSWORD, NONADMIN_FIRSTNAME, NONADMIN_SURNAME, false);

        //Test Copy
        userNonAdminCopy = new User(userNonAdmin);
    }

    @Test
    public void test_Constructor_Empty() {
        assertEquals(0, userEmpty.getId());
        assertNull(userEmpty.getUsername());
        assertNull(userEmpty.getPassword());
        assertNull(userEmpty.getFirstname());
        assertNull(userEmpty.getLastname());
    }

    @Test
    public void test_Constructor_WithId() {
        assertEquals(ADMIN_ID, userAdmin.getId());
        assertEquals(ADMIN_USERNAME, userAdmin.getUsername());
        assertNotEquals(ADMIN_PASSWORD, userAdmin.getPassword());
        assertEquals(Hash.hashingPassword(ADMIN_PASSWORD), userAdmin.getPassword());
        assertEquals(ADMIN_FIRSTNAME, userAdmin.getFirstname());
        assertTrue(userAdmin.getLastname().isEmpty());

        assertTrue(userAdmin.hasAdminPrivileges());
    }

    @Test
    public void test_Constructor_WithoutId() {
        assertNotEquals(NONADMIN_ID, userNonAdmin.getId());
        userNonAdmin.setId(NONADMIN_ID);
        assertEquals(NONADMIN_ID, userNonAdmin.getId());

        assertEquals(NONADMIN_USERNAME, userNonAdmin.getUsername());
        assertNotEquals(NONADMIN_PASSWORD, userNonAdmin.getPassword());
        assertEquals(Hash.hashingPassword(NONADMIN_PASSWORD), userNonAdmin.getPassword());
        assertEquals(NONADMIN_FIRSTNAME, userNonAdmin.getFirstname());
        assertEquals(NONADMIN_SURNAME, userNonAdmin.getLastname());

        assertFalse(userNonAdmin.hasAdminPrivileges());
    }

    @Test
    public void test_toString() {
        userNonAdminCopy.setId(NONADMIN_ID);

        assertNotEquals(userNonAdminCopy.toString(), userNonAdmin.toString());
    }

    @Test
    public void test_Setters(){
        int userId = -5;
        String username = "testSetUsername";
        String password = "testSetPassword";
        String firstname = "testSetFirstname";
        String surname = "testSetSurname";

        userEmpty.setId(userId);
        userEmpty.setUsername(username);
        userEmpty.setPassword(Hash.hashingPassword(password));
        userEmpty.setFirstname(firstname);
        userEmpty.setLastname(surname);
        userEmpty.setAdminPrivileges(false);

        assertEquals(userId, userEmpty.getId());
        assertEquals(username, userEmpty.getUsername());
        assertNotEquals(password, userEmpty.getPassword());
        assertEquals(Hash.hashingPassword(password), userEmpty.getFirstname());
        assertEquals(firstname, userEmpty.getFirstname());
        assertEquals(surname, userEmpty.getLastname());
        assertFalse(userEmpty.hasAdminPrivileges());

    }
}

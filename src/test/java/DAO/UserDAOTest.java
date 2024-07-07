package DAO;

import database.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.Hash;
import user.User;
import user.UserAttemptResult;
import quizz.quizz;
import quizz.defaultQuizz;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    static QuizzDAO quizzDAO;
    static UserDAO userDAO;
    static Connection connection;

    User testCreateUser, testGetUser;
    User testUser, testAdmin;
    User testFriend1, testFriend2;

    //Variables for AttemptTesting
    quizz sampleQuiz;
    User quizAuthor;
    UserAttemptResult userAttemptResult1, userAttemptResult2, userAttemptResultExtra;
    static double score1, score2, score3;

    private final String TEST_USERNAME = "testUserUsername";
    private final String TEST_ADMIN_USERNAME = "testAdminUsername";

    @BeforeAll
    public static void init() throws SQLException, ClassNotFoundException, IOException {
        connection = DBConnection.getConnection();
        userDAO = new UserDAO(connection);
        quizzDAO = new QuizzDAO(connection);

        score1 = Math.random();
        score2 = Math.random();
        score3 = Math.random();

        DBConnection.resetTables();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        testCreateUser = new User("create", Hash.hashingPassword("testCreate"), "create", "create", false);
        testGetUser = new User("get", Hash.hashingPassword("testGet"), "get", "get", false);
        testUser = new User(TEST_USERNAME, Hash.hashingPassword("testPassword"), "user", "test", false);
        testAdmin = new User(TEST_ADMIN_USERNAME, Hash.hashingPassword("testAdminPassword"), "admin", "admin", true);

        testFriend1 = new User("friend1", Hash.hashingPassword("testFriend1Password"), "friend", "1", false);
        testFriend2 = new User("friend2", Hash.hashingPassword("testFriend2Password"), "friend", "2", false);

        userDAO.removeUser(testUser.getId());
    }

    @Test
    public void test_CreateUser() throws SQLException {
        //Add User To DB
        userDAO.createUser(testCreateUser);

        //Get from DB Manually
        String query = "SELECT * FROM users;";
        PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();

        assertTrue(resultSet.last());

        int userId = resultSet.getInt(1);
        String username = resultSet.getString("username");
        String password = resultSet.getString("hashedPassword");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");

        User testUserFromDB = new User(userId, username, password, firstname, lastname, isAdmin);

        assertEquals(testCreateUser, testUserFromDB);

        System.out.println(testUserFromDB);

        //Delete from DB Manually
        deleteUserFromDB(testCreateUser.getId());
    }

    private void deleteUserFromDB(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.execute();
    }

    @Test
    public void test_DeleteUser() throws SQLException {
        //Add User To DB
        userDAO.createUser(testUser);
        userDAO.createUser(testAdmin);

        //Get from DB Manually
        String query = "SELECT * FROM users;";
        PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();

        assertTrue(resultSet.last());

        int userId = resultSet.getInt(1);
        String username = resultSet.getString("username");
        String password = resultSet.getString("hashedPassword");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");

        User testUserFromDB = new User(userId, username, password, firstname, lastname, isAdmin);

        assertEquals(testAdmin, testUserFromDB);

        //Delete User From DB
        userDAO.removeUser(testAdmin.getId());

        //Make Sure no Such User exists anymore in DB
        PreparedStatement preparedStatement2 = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet2 = preparedStatement.executeQuery();

        assertTrue(resultSet2.last());

        int userId2 = resultSet2.getInt(1);
        String username2 = resultSet2.getString("username");
        String password2 = resultSet2.getString("hashedPassword");
        boolean isAdmin2 = resultSet2.getBoolean("isAdmin");
        String firstname2 = resultSet2.getString("firstname");
        String lastname2 = resultSet2.getString("lastname");

        User testLastUserFromDB = new User(userId2, username2, password2, firstname2, lastname2, isAdmin2);

        assertNotEquals(testAdmin, testLastUserFromDB);

        System.out.println("Users To Compare After Deletion");
        System.out.println("Deleted User: " + testAdmin.toString());
        System.out.println("Current DB Last User: " + testLastUserFromDB.toString());

        userDAO.removeUser(testUser.getId());
    }

    //Requirement: createUser() and DeleteUser() already works
    @Test
    public void test_GetUserMethods() throws SQLException {
        //Add User To DB
        userDAO.createUser(testGetUser);

        //Get User with ID
        User userFromDBByID = userDAO.getUser(testGetUser.getId());
        assertEquals(testGetUser, userFromDBByID);

        //Get User with Username
        User userFromDBByUsername = userDAO.getUser(testGetUser.getUsername());
        assertEquals(testGetUser.getUsername(), userFromDBByUsername.getUsername());

        //Delete User from DB
        userDAO.removeUser(testGetUser.getId());
    }


    @Test
    public void test_GetCreatedQuizzes() throws SQLException, ClassNotFoundException {
        userDAO.createUser(testUser);

        assertEquals(0, userDAO.getCreatedQuizzes(testUser.getId()).size());

        sampleQuiz = new defaultQuizz("Sample Quiz", testUser, new ArrayList<>(), new ArrayList<>());

        //Test after quizz adding
        quizzDAO.addQuizz(sampleQuiz);

        assertEquals(1, userDAO.getCreatedQuizzes(testUser.getId()).size());

        //Test after quizz removing
        quizzDAO.removeQuizById(sampleQuiz.getId());

        assertEquals(0, userDAO.getCreatedQuizzes(testUser.getId()).size());

        //Test after quizz adding + user removing
        quizzDAO.addQuizz(sampleQuiz);

        assertEquals(1, userDAO.getCreatedQuizzes(testUser.getId()).size());

        userDAO.removeUser(testUser.getId());

        assertEquals(0, userDAO.getCreatedQuizzes(testUser.getId()).size());
    }

    @Test
    public void test_GetAttempts_Basic() throws SQLException, ClassNotFoundException {
        userDAO.createUser(testUser);

        sampleQuiz = new defaultQuizz("Sample Quiz", testUser, new ArrayList<>(), new ArrayList<>());

        quizzDAO.addQuizz(sampleQuiz);
        userAttemptResult1 = new UserAttemptResult(sampleQuiz.getId(), testUser.getId(), score1, new Timestamp(System.currentTimeMillis()));

        userDAO.addAttempt(userAttemptResult1);

        List<UserAttemptResult> attemptsListFromDB = userDAO.getAttempts(testUser.getId());
        assertEquals(1, attemptsListFromDB.size());

        assertEquals(userAttemptResult1, attemptsListFromDB.getFirst());

        userDAO.removeUser(testUser.getId());

        assertTrue(userDAO.getAttempts(testUser.getId()).isEmpty());
    }

    @Test
    public void test_AddAttempt_GetAttempts() throws SQLException, ClassNotFoundException {
        //Initialization
        userDAO.createUser(testUser);
        userDAO.createUser(testAdmin);

        quizAuthor = testAdmin;

        sampleQuiz = new defaultQuizz("Sample Quiz", quizAuthor, new ArrayList<>(), new ArrayList<>());

        quizzDAO.addQuizz(sampleQuiz);
        userAttemptResult1 = new UserAttemptResult(sampleQuiz.getId(), testUser.getId(), score1, new Timestamp(System.currentTimeMillis()));
        userAttemptResult2 = new UserAttemptResult(sampleQuiz.getId(), testUser.getId(), score2, new Timestamp(System.currentTimeMillis()));
        userAttemptResultExtra = new UserAttemptResult(sampleQuiz.getId(), testAdmin.getId(), score3, new Timestamp(System.currentTimeMillis()));

        //Adding extra Attempt
        userDAO.addAttempt(userAttemptResultExtra);

        //Add Several Attempts
        int numAttemptsBeforeAdding = getNumAttempts(sampleQuiz.getId());
        assertNotEquals(0, numAttemptsBeforeAdding);

        List<UserAttemptResult> attemptsToAdd = List.of(userAttemptResult1, userAttemptResult2);
        userDAO.addAttempt(userAttemptResult1);
        userDAO.addAttempt(userAttemptResult2);

        assertEquals(3, getNumAttempts(sampleQuiz.getId()));

        //Assert Attempts match the ones added in DB
        List<UserAttemptResult> attemptsListFromDB = userDAO.getAttempts(testUser.getId());

        assertEquals(attemptsToAdd, attemptsListFromDB);

        //Delete Attempts from DB
        userDAO.removeUser(testUser.getId());

        assertEquals(numAttemptsBeforeAdding, getNumAttempts(sampleQuiz.getId()));

        userDAO.removeUser(testAdmin.getId());
    }

    /* Returns number of attempts made for quiz */
    private int getNumAttempts(long quizId) throws SQLException {
        String query = "SELECT COUNT(*) FROM quizHistory WHERE quizId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, quizId);
        ResultSet resultSet = preparedStatement.executeQuery();

        assertTrue(resultSet.next());

        return resultSet.getInt(1);
    }

    @Test
    public void test_SetAdminPrivileges() throws SQLException {
        userDAO.createUser(testUser);

        assertFalse(testUser.hasAdminPrivileges());

        userDAO.setAdminPrivileges(testUser.getId(), true);
        testUser = userDAO.getUser(testUser.getId());

        //Assert Changes were made ToDo: privileges should affect somewhere to the functionality
        assertTrue(testUser.hasAdminPrivileges());

        userDAO.setAdminPrivileges(testUser.getId(), false);
        testUser = userDAO.getUser(testUser.getId());

        assertFalse(testUser.hasAdminPrivileges());

        userDAO.removeUser(testUser.getId());

        assertNotEquals(testUser, userDAO.getUser(testUser.getId()));
    }

    @Test
    public void test_AddFriend_GetFriends_Basic() throws SQLException, ClassNotFoundException {
        userDAO.createUser(testFriend1);
        userDAO.createUser(testFriend2);

        assertEquals(0, userDAO.getFriends(testFriend1.getId()).size());

        userDAO.addFriend(testFriend1.getId(), testFriend2.getId());

        Set<User> user1Friends = userDAO.getFriends(testFriend1.getId());
        Set<User> user2Friends = userDAO.getFriends(testFriend2.getId());

        assertEquals(1, user1Friends.size());
        assertEquals(1, user2Friends.size());

        assertTrue(user1Friends.contains(testFriend2));
        assertTrue(user2Friends.contains(testFriend1));

        userDAO.removeUser(testFriend1.getId());
        userDAO.removeUser(testFriend2.getId());

        assertEquals(0, userDAO.getFriends(testFriend1.getId()).size());
    }

    @Test
    public void test_AddFriend_GetFriends_MultipleFriends() throws SQLException {
        //User
        userDAO.createUser(testAdmin);

        //Friends
        userDAO.createUser(testUser);
        userDAO.createUser(testFriend1);
        userDAO.createUser(testFriend2);

        userDAO.addFriend(testAdmin.getId(), testUser.getId());
        userDAO.addFriend(testAdmin.getId(), testFriend1.getId());
        userDAO.addFriend(testAdmin.getId(), testFriend2.getId());

        assertEquals(3, userDAO.getFriends(testAdmin.getId()).size());
        assertEquals(1, userDAO.getFriends(testUser.getId()).size());
        assertEquals(1, userDAO.getFriends(testFriend1.getId()).size());
        assertEquals(1, userDAO.getFriends(testFriend2.getId()).size());

        Set<User> expectedAdminFriends = Set.of(testFriend1, testFriend2, testUser);
        Set<User> actualAdminFriends = userDAO.getFriends(testAdmin.getId());

        assertEquals(expectedAdminFriends, actualAdminFriends);

        //Partially Removing
        userDAO.removeUser(testFriend1.getId());
        userDAO.removeUser(testFriend2.getId());

        assertTrue(userDAO.getFriends(testAdmin.getId()).contains(testUser));

        userDAO.removeUser(testUser.getId());

        assertEquals(0, userDAO.getFriends(testAdmin.getId()).size());

        userDAO.removeUser(testAdmin.getId());
    }
}

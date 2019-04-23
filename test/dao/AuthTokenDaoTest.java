package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AuthTokenDaoTest {
    private DataBase db;
//    private AuthToken user1;
//    private AuthToken user2;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
//        user1 = new AuthToken("myName", null);
//        user2 = new AuthToken("anotherName", null);
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void addUserTokenPass() {
        String token = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            token = authDao.addUserToken("myName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        String user = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            user = authDao.getUserWithToken(token);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(user, "myName");

    }

    // omit the fail test case for addUserToken. Since it is always valid to add the same
    // user with auto generated auth token. It is unlikely auth token are the same. Thus, I'm
    // not doing negative on this one

    @Test
    public void getUserWithTokenPass() {
        String token2 = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.addUserToken("myName");
            token2 = authDao.addUserToken("anotherName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        String user = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            user = authDao.getUserWithToken(token2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(user, "anotherName");
    }


    @Test
    public void getUserWithTokenFail() {
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.addUserToken("myName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        String user = "Testing";
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            user = authDao.getUserWithToken("LowChanceThisIsAToken");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(user);
    }

    @Test
    public void deleteUserTokenPass() {
        String token = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            token = authDao.addUserToken("myName");
            authDao.addUserToken("anotherName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        String user = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.deleteUserToken("myName");
            user = authDao.getUserWithToken(token);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(user);
    }

    @Test
    public void deleteUserTokenFail() {
        String token = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            token = authDao.addUserToken("myName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        String user = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.deleteUserToken("MMyName"); // can't find it, so SQL exception
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
        }
        try {
            Connection conn = db.openConnection();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            user = authDao.getUserWithToken(token);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(user, "myName");
    }
}
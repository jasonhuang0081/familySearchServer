package dao;

import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoTest {
    private DataBase db;
    private User user;
    private User user2;

    @Before
    public void setup() throws Exception {
        db = new DataBase();
        db.createTables();
        user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "M", "ID12345");
        user2 = new User("anotherName", "ddd", "b@gmail.com", "Daniel",
                "Anderson", "M", "234345ggff");
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void insertUser() {
        User compareTest = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            compareTest = uDao.getUserWithName(user.getUserName());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(user, compareTest);
    }

    @Test
    public void insertUserFail() {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        // since it try to insert the same user twice, it fail and roll back
        assertFalse(didItWork);
        User compareTest = user;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            compareTest = uDao.getUserWithName(user.getUserName());
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void getUser() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            uDao.insertUser(user2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String username = user.getUserName();
            result = uDao.getUserWithName(username);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(result, user);

    }

    @Test
    public void getUserFail() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String username = user2.getUserName();
            result = uDao.getUserWithName(username);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result);
    }

    @Test
    public void deleteUserPass() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            uDao.insertUser(user2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String username = user2.getUserName();
            uDao.deleteUser(username);
            result = uDao.getUserWithName(username);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result);
    }

    @Test
    public void deleteUserFail() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String username = user2.getUserName();
            uDao.deleteUser(username);
            result = uDao.getUserWithName(user.getUserName());
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
        }
        assertNull(result);
    }

    @Test
    public void getUserWithIDPass() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            uDao.insertUser(user2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String ID = user2.getPersonID();
            result = uDao.getUserWithID(ID);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(result, user2);

    }

    @Test
    public void getUserWithIDFail() {
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User result = user;
        try                 // no user2 exist in data base
        {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            String ID = user2.getPersonID();
            result = uDao.getUserWithID(ID);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result);

    }
}
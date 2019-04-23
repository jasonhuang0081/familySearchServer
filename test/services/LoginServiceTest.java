package services;

import dao.DataBase;
import dao.UserDao;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LoginServiceTest {
    private DataBase db;
    private User user;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        user = new User("abc", "11111", "1@gmail.com", "jj"
                , "zolnic", "M", "myID");
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void loginPass() {
        LoginRequest request = new LoginRequest("abc", "11111");
        LoginService service = new LoginService();
        LoginResult result = service.login(request);
        User userOut = null;
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            userOut = uDao.getUserWithName(result.getUserName());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(userOut, user);    // used userName from the result to get user to check userName's validity
        assertEquals(result.getPersonID(), user.getPersonID());
        assertNotNull(result.getToken());
    }

    @Test
    public void loginFail() {
        LoginRequest request = new LoginRequest("abc", "WrongPass");
        LoginService service = new LoginService();
        LoginResult result = service.login(request);
        assertNull(result);
        LoginRequest request2 = new LoginRequest("NoBody", "11111");
        LoginResult result2 = service.login(request2);
        assertNull(result2);

    }
}
package services;

import dao.DataBase;
import dao.UserDao;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterRequest;
import results.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RegisterServiceTest {
    private DataBase db;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void registerPass() {
        RegisterRequest request = new RegisterRequest("dave123", "1223",
                "a@gmail.com", "dd", "bb", "F");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);
        assertEquals(result.getUserName(), "dave123");
        assertNotNull(result.getPersonID());
        assertNotNull(result.getToken());
        User user = null;
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            user = uDao.getUserWithName("dave123");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        User compare = new User("dave123", "1223", "a@gmail.com", "dd",
                "bb", "F", result.getPersonID());
        assertEquals(compare, user);
    }

    @Test
    public void registerFail() {
        RegisterRequest request = new RegisterRequest("dave123", "1223",
                "a@gmail.com", "dd", "bb", "F");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);
        assertEquals(result.getUserName(), "dave123");
        assertNotNull(result.getPersonID());
        assertNotNull(result.getToken());
        //  register with same user name
        RegisterRequest request2 = new RegisterRequest("dave123", "345",
                "bb@gmail.com", "abc", "eee", "M");
        RegisterResult result2 = service.register(request2);
        assertNull(result2);
    }
}
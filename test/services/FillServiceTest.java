package services;

import dao.DataBase;
import dao.UserDao;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.FillRequest;
import results.FillResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class FillServiceTest {

    private DataBase db;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        User user = new User("jackie", "abc", "a@gmail.com", "Jackie",
                "Jackson", "f", "ID12345");
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
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

    // another way to check the entry data is to use DB browser to see the entries
    @Test
    public void fillPass() {
        FillRequest request = new FillRequest("jackie", 2);
        FillService service = new FillService();
        FillResult result = service.fill(request);
        String comparedResult = "Successfully added 7.0 persons and 19.0 events to the database.";
        assertEquals(result.getMessage(), comparedResult);
        Connection connect = db.openConnection();
        try {
            Statement stmt = connect.createStatement();
            String sql = "SELECT COUNT(*) FROM person WHERE descendant = 'jackie' ";
            ResultSet rs = stmt.executeQuery(sql);
            int personCount = 0;
            while (rs.next()) {
                personCount = rs.getInt(1);
            }
            sql = "SELECT COUNT(*) FROM events WHERE descendant = 'jackie' ";
            rs = stmt.executeQuery(sql);
            int eventCount = 0;
            while (rs.next()) {
                eventCount = rs.getInt(1);
            }
            db.closeConnection(true);
            assertEquals(personCount, 7);
            assertEquals(eventCount, 19);

        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.println("SQL error - clear table ");
        }

        // testing if it will clear the related data and add new entries
        FillRequest request2 = new FillRequest("jackie", 1);
        FillService service2 = new FillService();
        FillResult result2 = service2.fill(request2);
        String comparedResult2 = "Successfully added 3.0 persons and 7.0 events to the database.";
        assertEquals(result2.getMessage(), comparedResult2);
        connect = db.openConnection();

        try {
            Statement stmt = connect.createStatement();
            String sql = "SELECT COUNT(*) FROM person WHERE descendant = 'jackie' ";
            ResultSet rs = stmt.executeQuery(sql);
            int personCount = 0;
            while (rs.next()) {
                personCount = rs.getInt(1);
            }
            sql = "SELECT COUNT(*) FROM events WHERE descendant = 'jackie' ";
            rs = stmt.executeQuery(sql);
            int eventCount = 0;
            while (rs.next()) {
                eventCount = rs.getInt(1);
            }
            db.closeConnection(true);
            assertEquals(personCount, 3);
            assertEquals(eventCount, 7);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.println("SQL error - clear table ");
        }
    }

    @Test
    public void fillFail() {
        FillRequest request = new FillRequest("noSuchAUser", 2);
        FillService service = new FillService();
        FillResult result = service.fill(request);
        String comparedResult = "Successfully added 7.0 persons and 19.0 events to the database.";
        assertNotSame(result.getMessage(), comparedResult);
        Connection connect = db.openConnection();
        try {
            Statement stmt = connect.createStatement();
            String sql = "SELECT COUNT(*) FROM person WHERE descendant = 'jackie' ";
            ResultSet rs = stmt.executeQuery(sql);
            int personCount = 1;
            while (rs.next()) {
                personCount = rs.getInt(1);
            }
            sql = "SELECT COUNT(*) FROM events WHERE descendant = 'jackie' ";
            rs = stmt.executeQuery(sql);
            int eventCount = 1;
            while (rs.next()) {
                eventCount = rs.getInt(1);
            }
            db.closeConnection(true);
            assertEquals(personCount, 0);
            assertEquals(eventCount, 0);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.println("SQL error - clear table ");
        }

    }
}
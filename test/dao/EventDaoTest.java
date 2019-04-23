package dao;

import model.Event;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventDaoTest {

    private DataBase db;
    private Event bestEvent;
    private Event anotherEvent;
    private Event thirdEvent;
    private User user;

    @Before
    public void setUp() throws Exception {

        db = new DataBase();
        bestEvent = new Event("Biking_123A", "David", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        anotherEvent = new Event("abc123", "myName", "david123",
                16.4f, 15.3f, "Korea", "Okinawa",
                "surfing", 1994);
        thirdEvent = new Event("ddd345", "myName", "david123",
                12f, 17f, "Taiwan", "Taipei",
                "shopping", 1984);
        user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "M", "ID12345");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void insertPass() {
        Event compareTest = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            compareTest = eDao.getEventWithID(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);

    }

    @Test
    public void insertFail() {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            eDao.insertEvent(bestEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Event compareTest = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            compareTest = eDao.getEventWithID(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);

    }

    @Test
    public void getEventbyIDPass() {
        Event result = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            eDao.insertEvent(anotherEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            result = eDao.getEventWithID(anotherEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(anotherEvent, result);
    }

    @Test
    public void getEventbyIDFail() {
        Event result = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            result = eDao.getEventWithID(anotherEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotSame(anotherEvent, result);
    }

    @Test
    public void getEventWithPersonPass() {
        List<Event> result = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            eDao.insertEvent(bestEvent);
            eDao.insertEvent(anotherEvent);
            eDao.insertEvent(thirdEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            result = eDao.getEventAll(user.getUserName());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        List<Event> compared = new ArrayList<>();
        compared.add(anotherEvent);
        compared.add(thirdEvent);

        assertEquals(compared, result);
    }

    @Test
    public void getEventWithPersonFail() {
        List<Event> result = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            eDao.insertEvent(bestEvent);
            eDao.insertEvent(anotherEvent);
            eDao.insertEvent(thirdEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        try {
            String userName = "somethingNotExist";
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            result = eDao.getEventAll(userName);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        List<Event> compared = new ArrayList<>();
        compared.add(anotherEvent);
        compared.add(thirdEvent);
        assertNotSame(compared, result);
    }

    @Test
    public void deletePerson() {
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            eDao.insertEvent(anotherEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Event result = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            String descendant = anotherEvent.getDescendant();
            eDao.deleteEvent(descendant);
            result = eDao.getEventWithID(anotherEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result);
    }

    @Test
    public void deletePersonFail() {
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insertEvent(bestEvent);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Event result = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            String descendant = anotherEvent.getDescendant();
            eDao.deleteEvent(descendant);
            result = eDao.getEventWithID(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (SQLException e)           // if it fail to delete anything, it will throws SQL exception
        {
            db.closeConnection(false);
        }
        assertEquals(result, bestEvent);
    }

}
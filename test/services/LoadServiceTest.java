package services;

import dao.DataBase;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoadRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LoadServiceTest {

    private DataBase db;
    private Person person;
    private Person person2;
    private User user;
    private User user2;
    private Event anotherEvent;
    private Event thirdEvent;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        person = new Person("a12345", "jason", "AJ", "Anderson",
                "m", "Dad", "mom", "");
        person2 = new Person("b234452", "jacob", "Amy", "Denning",
                "f", "papa", "mama", "Jared");
        user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "m", "ID12345");
        user2 = new User("anotherName", "ddd", "b@gmail.com", "Daniel",
                "Anderson", "m", "234345ggff");
        anotherEvent = new Event("abc123", "David", "david123",
                16.4f, 15.3f, "Korea", "Okinawa",
                "surfing", 1994);
        thirdEvent = new Event("ddd345", "David", "david123",
                12f, 17f, "Taiwan", "Taipei",
                "shopping", 1984);
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void loadPass() {
        List<Person> pList = new ArrayList<>();
        List<User> uList = new ArrayList<>();
        List<Event> eList = new ArrayList<>();
        pList.add(person);
        pList.add(person2);
        uList.add(user);
        uList.add(user2);
        eList.add(thirdEvent);
        eList.add(anotherEvent);
        LoadRequest request = new LoadRequest(uList, pList, eList);
        LoadService service = new LoadService();
        service.load(request);

        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            Event compared = eDao.getEventWithID(anotherEvent.getEventID());
            assertEquals(compared, anotherEvent);
            compared = eDao.getEventWithID(thirdEvent.getEventID());
            assertEquals(compared, thirdEvent);
            UserDao uDao = new UserDao(conn);
            User compared2 = uDao.getUserWithName(user.getUserName());
            assertEquals(compared2, user);
            compared2 = uDao.getUserWithName(user2.getUserName());
            assertEquals(compared2, user2);
            PersonDao pDao = new PersonDao(conn);
            Person compared3 = pDao.getPersonWithID(person.getPersonID());
            assertEquals(compared3, person);
            compared3 = pDao.getPersonWithID(person2.getPersonID());
            assertEquals(compared3, person2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void loadFail() {
        person = new Person("a12345", "", "", "Anderson",
                "M", "Dad", "mom", "");
        List<Person> pList = new ArrayList<>();
        List<User> uList = new ArrayList<>();
        List<Event> eList = new ArrayList<>();
        pList.add(person);
        LoadRequest request = new LoadRequest(uList, pList, eList);
        LoadService service = new LoadService();
        service.load(request);
        Person compared3;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            compared3 = pDao.getPersonWithID(person.getPersonID());
            assertNull(compared3);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }
}
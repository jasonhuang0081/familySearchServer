package services;

import dao.DataBase;
import dao.EventDao;
import dao.UserDao;
import model.Event;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.EventAllRequest;
import results.EventAllResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EventAllServiceTest {
    private DataBase db;
    private Event event1;
    private Event event2;
    private String token;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        event1 = new Event("Biking_123A", "myName", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        event2 = new Event("abc123", "myName", "david123",
                16.4f, 15.3f, "Korea", "Okinawa",
                "surfing", 1994);
        User user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "M", "ID12345");
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            UserDao uDao = new UserDao(conn);
            token = uDao.insertUser(user);
            eDao.insertEvent(event1);
            eDao.insertEvent(event2);
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
    public void eventAllPass() {
        EventAllRequest request = new EventAllRequest(token);
        EventAllService service = new EventAllService();
        EventAllResult result = service.eventAll(request);
        List<Event> compared = result.getData();
        List<Event> answer = new ArrayList<>();
        answer.add(event1);
        answer.add(event2);
        assertEquals(answer, compared);

    }

    @Test
    public void eventAllFail() {
        EventAllRequest request = new EventAllRequest("noSuchToken");
        EventAllService service = new EventAllService();
        EventAllResult result = service.eventAll(request);
        List<Event> compared = result.getData();
        assertNull(compared);

    }
}
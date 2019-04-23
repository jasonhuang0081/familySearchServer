package services;

import dao.DataBase;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.PersonRequest;
import results.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PersonServiceTest {
    private DataBase db;
    private Person person;
    private String token;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        person = new Person("a12345", "myName", "AJ", "Anderson",
                "M", "Dad", "mom", "");
        Person person2 = new Person("ID12345", "myName", "Amy", "Denning",
                "F", "papa", "mama", "Jared");
        User user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "M", "ID12345");
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            UserDao uDao = new UserDao(conn);
            token = uDao.insertUser(user);
            pDao.insertPerson(person);
            pDao.insertPerson(person2);
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
    public void personPass() {
        PersonRequest request = new PersonRequest("a12345", token);
        PersonService service = new PersonService();
        PersonResult result = service.person(request);
        Person compared = result.getPerson();
        assertEquals(person, compared);
    }

    @Test
    public void personFail() {
        PersonRequest request = new PersonRequest("a12345", ""); // no auth token
        PersonService service = new PersonService();
        PersonResult result = service.person(request);
        Person compared = result.getPerson();
        assertNull(compared);

    }
}
package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.PersonAllRequest;
import results.PersonAllResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PersonAllServiceTest {
    private DataBase db;
    private Person person;
    private Person person2;
    private String token;

    @Before
    public void setUp() throws Exception {
        db = new DataBase();
        db.createTables();
        person = new Person("a12345", "myName", "AJ", "Anderson",
                "M", "Dad", "mom", "");
        person2 = new Person("ID12345", "myName", "Amy", "Denning",
                "F", "papa", "mama", "Jared");
        User user = new User("myName", "abc", "a@gmail.com", "J",
                "Jackson", "M", "ID12345");
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            UserDao uDao = new UserDao(conn);
            uDao.insertUser(user);
            AuthTokenDao aDao = new AuthTokenDao(conn);
            token = aDao.addUserToken("myName");
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
    public void personAllPass() {
        PersonAllRequest request = new PersonAllRequest(token);
        PersonAllService service = new PersonAllService();
        PersonAllResult result = service.personAll(request);
        List<Person> compared = result.getData();
        List<Person> answer = new ArrayList<>();
        answer.add(person);
        answer.add(person2);
        assertEquals(answer, compared);

    }

    @Test
    public void personAllFail() {
        PersonAllRequest request = new PersonAllRequest("noSuchToken");
        PersonAllService service = new PersonAllService();
        PersonAllResult result = service.personAll(request);
        List<Person> compared = result.getData();
        assertNull(compared);

    }
}
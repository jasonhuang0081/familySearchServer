package services;

import dao.DataBase;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;

public class ClearServiceTest {
    private DataBase db;
    private final Person person = new Person("a12345", "jacob", "AJ", "Anderson",
            "M", "Dad", "mom", "");
    private final User user = new User("myName", "abc", "a@gmail.com", "J",
            "Jackson", "M", "ID12345");

    @Before
    public void setup() {

        try {
            db = new DataBase();
            db.createTables();
            Connection con = db.openConnection();
            PersonDao pDao = new PersonDao(con);
            UserDao uDao = new UserDao(con);
            pDao.insertPerson(person);
            uDao.insertUser(user);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }

    }

    @Test
    public void clear() {
        Person result1 = person;
        User result2 = user;
        try {
            db.clearTables();
            db.createTables();
            Connection con = db.openConnection();
            PersonDao pDao = new PersonDao(con);
            UserDao uDao = new UserDao(con);
            result1 = pDao.getPersonWithID("a12345");
            result2 = uDao.getUserWithName("myName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result1);
        assertNull(result2);
    }
}
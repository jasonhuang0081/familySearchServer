package dao;

import model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonDaoTest {
    private DataBase db;
    private Person person;
    private Person person2;
    private Person ancestor;

    @Before
    public void setup() throws Exception {
        db = new DataBase();
        db.createTables();
        person = new Person("a12345", "myName", "AJ", "Anderson",
                "M", "Dad", "mom", "");
        person2 = new Person("b234452", "myName", "Amy", "Denning",
                "F", "papa", "mama", "Jared");
        ancestor = new Person("g2345", "b234452", "Aron", "whitening",
                "M", "papa2", "mama2", "");

    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void insertPerson() {
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            compareTest = pDao.getPersonWithID(person.getPersonID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(person, compareTest);
    }

    @Test
    public void insertPersonFail() {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            pDao.insertPerson(person);
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        // since it try to insert the same user twice, it fail and roll back
        assertFalse(didItWork);
        Person compareTest = person;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            compareTest = pDao.getPersonWithID(person.getPersonID());
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);

    }

    @Test
    public void getPersonWithID() {
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            pDao.insertPerson(person2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Person result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            String id = person.getPersonID();
            result = pDao.getPersonWithID(id);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertEquals(result, person);
    }

    @Test
    public void getPersonWithIDFail() {
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Person result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            String id = person2.getPersonID();
            result = pDao.getPersonWithID(id);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(result);

    }

    @Test
    public void deletePerson() {
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            pDao.insertPerson(person2);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Person result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            String descendant = person2.getDescendant();
            pDao.deletePerson(descendant);
            result = pDao.getPersonWithID(person2.getPersonID());
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
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        Person result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            String descendant = person2.getPersonID();
            pDao.deletePerson(descendant);
            result = pDao.getPersonWithID(person.getPersonID());
            db.closeConnection(true);
        } catch (SQLException e)           // if it fail to delete anything, it will throws SQL exception
        {
            db.closeConnection(false);
        }
        assertEquals(result, person);
    }

    @Test
    public void getAllPersonPass() {
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            pDao.insertPerson(person2);
            pDao.insertPerson(ancestor);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        List<Person> result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            result = pDao.getAllPerson("myName");
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        List<Person> compared = new ArrayList<>();
        compared.add(person);
        compared.add(person2);
        assertEquals(compared, result);
    }

    @Test
    public void getAllPersonFail() {
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.insertPerson(person);
            pDao.insertPerson(person2);
            pDao.insertPerson(ancestor);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        List<Person> result = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            String userName = "SomethingNotExist";
            result = pDao.getAllPerson(userName);
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(result);
        assertEquals(result.size(), 0);

    }
}
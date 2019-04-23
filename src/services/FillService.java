package services;

import dao.DataBase;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import requests.FillRequest;
import results.FillResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

/**
 * Populates the server's database with generated data for the specified user name.
 * The required username parameter must be a user already registered with the server. If there is
 * any data in the database already associated with the given user name, it is deleted. The
 * optional generations parameter lets the caller specify the number of generations of ancestors
 * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 * persons each with associated events).
 */
public class FillService {
    /**
     * It will randomly populate the generation into data base, then
     * print if it is success or not
     *
     * @param fillRequest a request used specifically in this service
     */
    public FillResult fill(FillRequest fillRequest) {

        GenData data = new GenData(fillRequest.getUserName());
        Random rand = new Random();
        int n = rand.nextInt(10);
        int userAge = 20 + n;               //get user's age randomly between 20 and 30
        int generation = fillRequest.getNumGeneration();
        DataBase db = new DataBase();
        try {

            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            User user = uDao.getUserWithName(fillRequest.getUserName());
            Person person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(),
                    user.getGender(), GenData.genID(15), GenData.genID(15), null);
            PersonDao pDao = new PersonDao(con);
            pDao.deletePerson(user.getUserName());
            pDao.insertPerson(person);
            Event event = data.genEvent(person.getPersonID(), 1990 - userAge,
                    "birth", person.getPersonID() + "_birth");
            EventDao eDao = new EventDao(con);
            eDao.deleteEvent(user.getUserName());
            eDao.insertEvent(event);
            insertParents(person, data, pDao, eDao, event.getYear(), generation);
            db.closeConnection(true);
            double personAdded = 2 * Math.pow(2, generation) - 1;
            double eventAdded = (personAdded - 1) * 3 + 1;
            return new FillResult("Successfully added " + personAdded + " persons and " +
                    eventAdded + " events to the database.");
        } catch (NullPointerException ne) {
            //ne.printStackTrace();
            db.closeConnection(false);
            return new FillResult("User name is not in data base");
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new FillResult("Internal server error");
        }


    }

    private void insertParents(Person person, GenData data, PersonDao pDao, EventDao eDao,
                               int personBirth, int generation) throws SQLException {
        if (generation == 0) {
            return;
        }
        Person dad = data.genPerson("m",
                person.getMother(), person.getFather());
        Person mom = data.genPerson("f",
                person.getFather(), person.getMother());

        Event marriage_dad = data.genEvent(dad.getPersonID(), personBirth - 3,
                "marriage", dad.getPersonID() + "_marriage");
        Event marriage_mom = new Event(mom.getPersonID() + "_marriage", marriage_dad.getDescendant(),
                mom.getPersonID(), marriage_dad.getLatitude(), marriage_dad.getLongitude(),
                marriage_dad.getCountry(), marriage_dad.getCity(), "marriage", marriage_dad.getYear());
        Event dead_dad = data.genEvent(dad.getPersonID(), personBirth + 50, "dead", dad.getPersonID() + "_dead");
        Event dead_mom = data.genEvent(mom.getPersonID(), personBirth + 55, "dead", mom.getPersonID() + "_dead");
        Event birth_dad = data.genEvent(dad.getPersonID(), personBirth - 26, "birth", dad.getPersonID() + "_birth");
        Event birth_mom = data.genEvent(mom.getPersonID(), personBirth - 23, "birth", mom.getPersonID() + "_birth");
        pDao.insertPerson(dad);
        pDao.insertPerson(mom);
        eDao.insertEvent(marriage_dad);
        eDao.insertEvent(marriage_mom);
        eDao.insertEvent(dead_dad);
        eDao.insertEvent(dead_mom);
        eDao.insertEvent(birth_dad);
        eDao.insertEvent(birth_mom);
        generation--;
        insertParents(dad, data, pDao, eDao, birth_dad.getYear(), generation);
        insertParents(mom, data, pDao, eDao, birth_mom.getYear(), generation);
    }
}

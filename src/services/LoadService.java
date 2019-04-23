package services;


import dao.DataBase;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import requests.LoadRequest;
import results.LoadResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clears all data from the database (just like the /clear API), and then loads the
 * posted user, person, and event data into the database
 */
public class LoadService {

    /**
     * It print the statement to tell if it successfully add users, persons, events to data
     * base
     *
     * @param loadRequest a request used specifically in this service
     */
    public LoadResult load(LoadRequest loadRequest) {
        ClearService service = new ClearService();
        service.clear();
        DataBase db = new DataBase();
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            for (User each : loadRequest.getUsers()) {
                if (checkUsers(each)) {
                    db.closeConnection(false);
                    return new LoadResult("Invalid request data in Users(missing values, invalid values, etc.)");
                }
                uDao.insertUser(each);
            }
            EventDao eDao = new EventDao(con);
            for (Event each : loadRequest.getEvents()) {
                if (checkEvents(each)) {
                    db.closeConnection(false);
                    return new LoadResult("Invalid request data in Events(missing values, invalid values, etc.)");
                }
                eDao.insertEvent(each);
            }
            PersonDao pDao = new PersonDao(con);
            for (Person each : loadRequest.getPersons()) {
                if (checkPersons(each)) {
                    db.closeConnection(false);
                    return new LoadResult("Invalid request data in Persons(missing values, invalid values, etc.)");
                }
                pDao.insertPerson(each);
            }
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            return new LoadResult("Internal error");
        }

        return new LoadResult("Successfully added " + loadRequest.getUsers().size() + " users, " +
                loadRequest.getPersons().size() + " persons, and " + loadRequest.getEvents().size() +
                " events to the database.");
    }

    private boolean checkUsers(User each) {
        return each.getPassword() == null || each.getUserName() == null || each.getEmail() == null ||
                each.getFirstName() == null || each.getLastName() == null || each.getGender() == null ||
                each.getPersonID() == null || each.getPassword().equals("") || each.getUserName().equals("") ||
                each.getEmail().equals("") || each.getFirstName().equals("") || each.getLastName().equals("") ||
                each.getGender().equals("") || each.getPersonID().equals("") ||
                !(each.getGender().equals("f") || each.getGender().equals("m"));
    }

    private boolean checkEvents(Event each) {
        return each.getEventID() == null || each.getDescendant() == null || each.getPersonID() == null ||
                each.getCountry() == null || each.getCity() == null || each.getEventType() == null ||
                each.getEventID().equals("") || each.getDescendant().equals("") || each.getPersonID().equals("") ||
                each.getCountry().equals("") || each.getCity().equals("") || each.getEventType().equals("");
    }

    private boolean checkPersons(Person each) {
        return each.getPersonID() == null || each.getDescendant() == null || each.getFirstName() == null ||
                each.getLastName() == null || each.getGender() == null ||
                each.getPersonID().equals("") || each.getDescendant().equals("") || each.getFirstName().equals("") ||
                each.getLastName().equals("") || each.getGender().equals("") ||
                !(each.getGender().equals("f") || each.getGender().equals("m"));
    }
}

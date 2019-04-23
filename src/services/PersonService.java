package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import requests.PersonRequest;
import results.PersonResult;

import java.sql.Connection;

/**
 * ​Returns the single Event object with the specified ID
 */
public class PersonService {
    /**
     * It will return personResult with the exception handling inside the function
     *
     * @param personRequest a request that is used in this service
     * @return PersonResult object
     */
    public PersonResult person(PersonRequest personRequest) {
        DataBase db = new DataBase();
        try {
            Connection con = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(con);
            String userName = aDao.getUserWithToken(personRequest.getToken());
            if (userName == null) {
                db.closeConnection(false);
                return new PersonResult(null, "Auth Token not exist");
            }
            PersonDao pDao = new PersonDao(con);
            Person person = pDao.getPersonWithID(personRequest.getPersonID());
            String descendant = person.getDescendant();
            UserDao uDao = new UserDao(con);
            User user = uDao.getUserWithName(userName);
            Person currentUser = pDao.getPersonWithID(user.getPersonID());
            db.closeConnection(true);
            if (!descendant.equals(currentUser.getDescendant())) {
                return new PersonResult(null, " Requested person does not belong to this user");
            }
            return new PersonResult(person, null);
        } catch (Exception e) {
            //e.printStackTrace();
            db.closeConnection(false);
            return new PersonResult(null, "No such a person associate with this ID");
        }
    }
}

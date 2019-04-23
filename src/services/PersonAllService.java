package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import requests.PersonAllRequest;
import results.PersonAllResult;

import java.sql.Connection;
import java.util.List;

/**
 * Returns ALL family members of the current user. The current user is
 * determined from the provided auth token.
 */
public class PersonAllService {
    /**
     * It will use the person request to form person result
     *
     * @param personAllRequest a request that's used in this service
     * @return PersonALLResult object that contains all the person matches request
     */
    public PersonAllResult personAll(PersonAllRequest personAllRequest) {
        DataBase db = new DataBase();
        try {
            Connection con = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(con);
            String userName = aDao.getUserWithToken(personAllRequest.getToken());
            if (userName == null) {
                db.closeConnection(false);
                return new PersonAllResult(null, "Auth Token not exist");
            }
            UserDao uDao = new UserDao(con);
            User user = uDao.getUserWithName(userName);
            PersonDao pDao = new PersonDao(con);
            List<Person> person = pDao.getAllPerson(user.getUserName());
            db.closeConnection(true);
            return new PersonAllResult(person, null);
        } catch (Exception e) {
            //e.printStackTrace();
            db.closeConnection(false);
            return new PersonAllResult(null, "Internal error");
        }
    }
}

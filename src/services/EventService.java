package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.EventDao;
import dao.UserDao;
import model.Event;
import model.User;
import requests.EventRequest;
import results.EventResult;

import java.sql.Connection;

/**
 * ​Returns the single Event object with the specified ID
 */
public class EventService {

    /**
     * This function process Event request and return the result
     *
     * @param eventRequest a request used specifically in this service
     * @return EventResult object
     */
    public EventResult event(EventRequest eventRequest) {
        DataBase db = new DataBase();
        try {
            Connection con = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(con);
            String userName = aDao.getUserWithToken(eventRequest.getToken());
            if (userName == null) {
                db.closeConnection(false);
                return new EventResult(null, "Auth Token not exist");
            }
            EventDao eDao = new EventDao(con);
            Event event = eDao.getEventWithID(eventRequest.getEventID());
            String descendant = event.getDescendant();
            UserDao uDao = new UserDao(con);
            User user = uDao.getUserWithName(userName);
//            Person currentUser = pDao.getPersonWithID(user.getPersonID());
            db.closeConnection(true);
            if (!descendant.equals(user.getUserName())) {
                return new EventResult(null, " Requested event does not belong to this user");
            }
            return new EventResult(event, null);
        } catch (Exception e) {
            //e.printStackTrace();
            db.closeConnection(false);
            return new EventResult(null, "No such a person associate with this ID");
        }
    }
}

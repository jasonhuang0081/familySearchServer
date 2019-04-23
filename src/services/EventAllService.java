package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.EventDao;
import dao.UserDao;
import model.Event;
import model.User;
import requests.EventAllRequest;
import results.EventAllResult;

import java.sql.Connection;
import java.util.List;

/**
 * ​Returns ALL events for ALL family members of the current user. The current
 * user is determined from the provided auth token.
 */
public class EventAllService {

    /**
     * It return all the events that is requested by eventRequest
     * It will handle the exception inside the function
     *
     * @param eventRequest a request used specifically in this service
     * @return EventResult object
     */
    public EventAllResult eventAll(EventAllRequest eventRequest) {
        DataBase db = new DataBase();
        try {
            Connection con = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(con);
            String userName = aDao.getUserWithToken(eventRequest.getToken());
            if (userName == null) {
                db.closeConnection(false);
                return new EventAllResult(null, "Auth Token not exist");
            }
            UserDao uDao = new UserDao(con);
            User user = uDao.getUserWithName(userName);
            EventDao eDao = new EventDao(con);
            List<Event> events = eDao.getEventAll(user.getUserName());
            db.closeConnection(true);
            return new EventAllResult(events, null);
        } catch (Exception e) {
            //e.printStackTrace();
            db.closeConnection(false);
            return new EventAllResult(null, "Internal error");
        }
    }
}

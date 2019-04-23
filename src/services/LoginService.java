package services;

import dao.AuthTokenDao;
import dao.DataBase;
import dao.UserDao;
import model.User;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Logs in the user and returns an auth token.
 */
public class LoginService {
    /**
     * It process the request and return the Result object
     *
     * @param loginRequest a request that is used in this service
     * @return LoginResult object
     */
    public LoginResult login(LoginRequest loginRequest) {
        DataBase db = new DataBase();
        LoginResult result = null;
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            User currentUser = uDao.getUserWithName(loginRequest.getUserName());
            if (currentUser == null || !loginRequest.getPassword().equals(currentUser.getPassword())) {
                db.closeConnection(false);
                return null;
            }
            AuthTokenDao auDao = new AuthTokenDao(con);
            String token = auDao.addUserToken(currentUser.getUserName());
            result = new LoginResult(token, currentUser.getUserName(), currentUser.getPersonID());
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        return result;
    }
}

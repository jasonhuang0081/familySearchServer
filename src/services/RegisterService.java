package services;

import dao.DataBase;
import dao.UserDao;
import model.User;
import requests.FillRequest;
import requests.RegisterRequest;
import results.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates a new user account, generates 4 generations of ancestor data for the new
 * user, logs the user in, and returns an auth token
 */
public class RegisterService {
    /**
     * This function will takes in the request and generate result according to description
     *
     * @param registerRequest a request that's used in this service
     * @return RegisterResult object
     */
    public RegisterResult register(RegisterRequest registerRequest) {
        DataBase db = new DataBase();
        RegisterResult result = null;
        try {
            Connection con = db.openConnection();
            UserDao uDao = new UserDao(con);
            String ID = GenData.genID(15);
            String token = uDao.insertUser(new User(registerRequest.getUserName(), registerRequest.getPassword(),
                    registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(),
                    registerRequest.getGender(), ID));
            db.closeConnection(true);
            FillRequest request = new FillRequest(registerRequest.getUserName(), 4);
            FillService service = new FillService();
            service.fill(request);
            result = new RegisterResult(token, registerRequest.getUserName(), ID);
        } catch (SQLException e) {
            // e.printStackTrace();
            db.closeConnection(false);
        }
        return result;
    }


//    private String genID()
//    {
//        return UUID.randomUUID().toString();
//    }
}

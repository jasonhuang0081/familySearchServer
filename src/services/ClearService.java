package services;

import dao.DataBase;
import results.ClearResult;

import java.sql.SQLException;

/**
 * Deletes ALL data from the database, including user accounts, auth tokens, and
 * generated person and event data.
 */
public class ClearService {

    /**
     * This function delete all data from data base
     */
    public ClearResult clear() {
        String message;
        DataBase db = new DataBase();
        try {
            db.clearTables();
            db.createTables();
            message = "Clear succeeded.";
        } catch (SQLException e) {
            e.printStackTrace();
            message = "clear table service error";
            db.closeConnection(false);
        }
        return new ClearResult(message);
    }

}

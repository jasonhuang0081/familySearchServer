package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class use the connection to insert and get user from
 * the data base
 */
public class UserDao {
    /**
     * connection to data base
     */
    private final Connection connect;

    /**
     * use the connection to talk between data base and User object
     *
     * @param connect a connection between data base and JDBC
     */
    public UserDao(Connection connect) {
        this.connect = connect;
    }

    /**
     * Register the user into the data base
     *
     * @param user a person model object
     * @throws SQLException exception that can happen using SQL
     */
    public String insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user" +
                "(username , password , email, firstname , lastname , gender , personID )" +
                "values(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            if (stmt.executeUpdate() == 1) {
                AuthTokenDao authDao = new AuthTokenDao(connect);
                String token = null;
                while (token == null) {
                    token = authDao.addUserToken(user.getUserName());
                }
                return token;
            } else {
                throw new SQLException();
            }
        }
    }

    /**
     * Get the user by using user name
     *
     * @param userName unique login in string
     * @return User
     * @throws SQLException exception that can happen using SQL
     */
    public User getUserWithName(String userName) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        User user = null;
        String sql = "select * from user where username = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("gender"),
                        rs.getString("personID"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return user;
    }

    public User getUserWithID(String personID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        User user = null;
        String sql = "select * from user where personID = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("gender"),
                        rs.getString("personID"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return user;
    }

    public void deleteUser(String userName) throws SQLException {
        String sql = "delete from user where username = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, userName);
            if (stmt.executeUpdate() == 1) {
                AuthTokenDao authDao = new AuthTokenDao(connect);
                authDao.deleteUserToken(userName);
            } else {
                throw new SQLException();
            }

        }
    }
}

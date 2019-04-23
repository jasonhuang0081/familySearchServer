package dao;

import services.GenData;

import java.sql.*;

/**
 * This class use connection to get data to and from data base
 */
public class AuthTokenDao {
    /**
     * connection to data base
     */
    private final Connection connect;

    /**
     * Use connection to construct the class
     *
     * @param connect a connection between data base and JDBC
     */
    public AuthTokenDao(Connection connect) {
        this.connect = connect;
    }

    /**
     * It will call generate token function and add token and user to data base
     *
     * @param userName user's login identity
     */
    public String addUserToken(String userName) throws SQLException {
        String token = GenData.genID(15);
        String sql = "INSERT INTO auth" +
                "(token , username )" +
                "values(?,?)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, userName);
            if (stmt.executeUpdate() == 1) {
                return token;
            } else {
                throw new SQLException();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        }

    }

    /**
     * It uses authToken to find the user
     *
     * @param authToken AuthToken
     * @return the user name that it matches
     */
    public String getUserWithToken(String authToken) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String userName = null;
        String sql = "select username from auth where token = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userName = rs.getString("username");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return userName;
    }

    public void deleteUserToken(String userName) throws SQLException {
        String sql = "delete from auth where username  = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, userName);
            if (stmt.executeUpdate() != 1) {
                throw new SQLException();
            }
        }
    }


}

package requests;

/**
 * generate the request to be used in login service
 */
public class LoginRequest {
    /**
     * user's login name
     */
    private final String userName;
    /**
     * user's pass work for login
     */
    private final String password;

    /**
     * construct the request
     *
     * @param userName user's login name
     * @param password user's pass work for login
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }
}

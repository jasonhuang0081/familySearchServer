package requests;

/**
 * Request for RegisterService service
 */
public class RegisterRequest {
    /**
     * Unique user name
     */
    private final String userName;
    /**
     * User’s password
     */
    private final String password;
    /**
     * User’s email address
     */
    private final String email;
    /**
     * User’s email address
     */
    private final String firstName;
    /**
     * User’s email address
     */
    private final String lastName;
    /**
     * User’s email address
     */
    private final String gender;

    /**
     * The constructor creates a request object
     *
     * @param userName  Unique user name
     * @param password  User’s password
     * @param email     User’s email address
     * @param firstName User’s email address
     * @param lastName  User’s email address
     * @param gender    User’s email address
     */
    public RegisterRequest(String userName, String password,
                           String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }


    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getGender() {
        return gender;
    }
}

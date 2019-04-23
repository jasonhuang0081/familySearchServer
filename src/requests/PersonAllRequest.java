package requests;

/**
 * Use auth-token to determine the user and form a request
 */
public class PersonAllRequest {
    /**
     * AuthToken
     */
    private final String token;

    /**
     * use token to generate the object
     *
     * @param token AuthToken
     */
    public PersonAllRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

package requests;

/**
 * This class will be used as the request in EventAllService class
 */
public class EventAllRequest {
    /**
     * AuthToken
     */
    private String token;

    /**
     * It uses the auth-token to generate the request that is used in eventAll service
     *
     * @param token AuthToken
     */
    public EventAllRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

package requests;

/**
 * This class is a request which is used in EventService
 */
public class EventRequest {
    /**
     * AuthToken
     */
    private final String token;
    /**
     * event's ID
     */
    private final String eventID;

    /**
     * It construct the request by taking in the following parameters
     *
     * @param token   AuthToken
     * @param eventID event's unique ID
     */
    public EventRequest(String token, String eventID) {
        this.token = token;
        this.eventID = eventID;
    }

    public String getToken() {
        return token;
    }

    public String getEventID() {
        return eventID;
    }
}

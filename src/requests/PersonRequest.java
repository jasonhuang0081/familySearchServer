package requests;

/**
 * This generate the request with person ID
 */
public class PersonRequest {
    /**
     * unique ID for each peron
     */
    private final String personID;
    private final String token;

    /**
     * construct the object of the request with personID
     *
     * @param personID unique ID for each peron
     */
    public PersonRequest(String personID, String token) {
        this.personID = personID;
        this.token = token;
    }

    public String getPersonID() {
        return personID;
    }

    public String getToken() {
        return token;
    }
}

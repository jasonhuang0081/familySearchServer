package results;

import model.Person;

/**
 * This is the result, which is condensed into a Person object to keep simply
 */
public class PersonResult {
    /**
     * All the results can be contain in a person object
     */
    private final Person person;
    private String message;

    /**
     * return the result of a person
     *
     * @param person a person object
     */
    public PersonResult(Person person, String message) {
        this.person = person;
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package results;

import model.Person;

import java.util.List;

/**
 * This is the result generated by PersonAll Service
 */
public class PersonAllResult {
    /**
     * a list of person object that's in user's family
     */
    private List<Person> data;
    private String message;

    /**
     * constructor that takes a list of person and generate JSON object
     *
     * @param data a list of person object
     */
    public PersonAllResult(List<Person> data, String message) {
        this.data = data;
        this.message = message;
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

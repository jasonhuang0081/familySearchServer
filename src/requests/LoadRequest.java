package requests;

import model.Event;
import model.Person;
import model.User;

import java.util.List;

/**
 * This generate a list of user, person, events to be used in load service
 */
public class LoadRequest {
    /**
     * a lit of user object
     */
    private List<User> users;

    /**
     * a list of person object
     */
    private List<Person> persons;
    /**
     * a list of event object
     */
    private List<Event> events;

    /**
     * assign the data members and construct the object
     *
     * @param users   a lit of user object
     * @param persons a list of person object
     * @param events  a list of event object
     */
    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }

}

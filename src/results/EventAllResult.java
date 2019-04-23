package results;

import model.Event;

import java.util.List;

/**
 * This is the result generated in EventAllService
 */
public class EventAllResult {
    /**
     * an array of event objects for the user's all family
     */
    private List<Event> data;
    private String message;

    /**
     * This construct the result of many events found in EventAllService
     *
     * @param events an events array
     */
    public EventAllResult(List<Event> events, String message) {
        this.data = events;
        this.message = message;
    }

    public List<Event> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}

package model;

/**
 * This class contains all the components of the data in a event
 */
public class Event {
    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;
    /**
     * User (Username) to which this person belongs
     */
    private String descendant;
    /**
     * ID of person to which this event belongs
     */
    private String personID;
    /**
     * Latitude of event’s location
     */
    private double latitude;
    /**
     * Longitude of event’s location
     */
    private double longitude;
    /**
     * Country in which event occurred
     */
    private String country;
    /**
     * City in which event occurred
     */
    private String city;
    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;
    /**
     * Year in which event occurred
     */
    private int year;

    /**
     * Initialize the event model with all the parameters in it
     *
     * @param eventID   Unique identifier for this event (non-empty string)
     * @param username  User (Username) to which this person belongs
     * @param personID  ID of person to which this event belongs
     * @param latitude  Latitude of event’s location
     * @param longitude Longitude of event’s location
     * @param country   Country in which event occurred
     * @param city      City in which event occurred
     * @param eventType Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year      Year in which event occurred
     */
    public Event(String eventID, String username, String personID, double latitude, double longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


    public String getEventID() {
        return eventID;
    }


    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }


//    public void setUsername(String username) {
//        this.descendant = username;
//    }

    public String getPersonID() {
        return personID;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public String getCountry() {
        return country;
    }


    public String getCity() {
        return city;
    }


    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }

    /**
     * This compare all the data members to see if they are all equal
     *
     * @param o another Event object
     * @return if two events are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof model.Event) {
            model.Event oEvent = (model.Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getDescendant().equals(getDescendant()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        }
        return false;
    }
}

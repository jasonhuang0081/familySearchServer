package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class can insert and retrieve events between data base
 */
public class EventDao {
    /**
     * connection to data base
     */
    private final Connection connect;

    /**
     * constructor that uses connection to
     * connect between object and data base
     *
     * @param connect a connection between data base and JDBC
     */
    public EventDao(Connection connect) {
        this.connect = connect;
    }

    /**
     * Insert the event to data base
     *
     * @param event an Event object
     * @throws SQLException exception that can happen using SQL
     */
    public void insertEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events" +
                "(eventID , descendant, personID , latitude , longtitude , country ," +
                " city , eventtype , year )" +
                "values(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());
            if (stmt.executeUpdate() != 1) {
                throw new SQLException();
            }
        }
    }

    /**
     * It will find the event by the given event ID
     *
     * @param eventID Event ID
     * @return Event
     * @throws SQLException exception that can happen using SQL
     */
    public Event getEventWithID(String eventID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Event event = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("descendant"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longtitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventtype"),
                        rs.getInt("year"));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return event;
    }

    /**
     * This will return an array of events that is associate with a person
     * It takes personID, so both user and person have it
     *
     * @param userName Unique identifier for this person
     * @return an array of events
     * @throws SQLException exception that can happen using SQL
     */
    public List<Event> getEventAll(String userName) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Event> eventList = new ArrayList<>();
        String sql = "select eventID from events where descendant = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Event temp = getEventWithID(rs.getString("EventID"));
                eventList.add(temp);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return eventList;
    }

    public void deleteEvent(String descendant) //throws SQLException
    {
        String sql = "delete from events where descendant = ?";
        PreparedStatement stmt;
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, descendant);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

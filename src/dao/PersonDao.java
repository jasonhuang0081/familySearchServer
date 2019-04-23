package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class use the connection to insert a person and retrieve
 * a person from data base
 */
public class PersonDao {
    /**
     * connection to data base
     */
    private final Connection connect;

    /**
     * Construct dao that connect between Person and data base
     *
     * @param connect a connection between data base and JDBC
     */
    public PersonDao(Connection connect) {
        this.connect = connect;
    }

    /**
     * It will insert Person to data base
     *
     * @param person a Person model object
     * @throws SQLException exception that can happen using SQL
     */
    public void insertPerson(Person person) throws SQLException {
        String sql = "INSERT INTO person" +
                "(personID, descendant, firstname, lastname, gender, father, mother, spouse)" +
                "values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getDescendant());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFather());
            stmt.setString(7, person.getMother());
            stmt.setString(8, person.getSpouse());
            if (stmt.executeUpdate() != 1) {
                throw new SQLException();
            }
        }
    }

    /**
     * It gets the person from data base with given person ID
     * This can be used in person service or All person service
     *
     * @param personID Person's ID
     * @return Person
     * @throws SQLException exception that can happen using SQL
     */
    public Person getPersonWithID(String personID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Person person = null;
        String sql = "select * from person where personID = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"),
                        rs.getString("descendant"),
                        rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("gender"), rs.getString("father"),
                        rs.getString("mother"), rs.getString("spouse"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return person;
    }

    /**
     * It return a list of person that's associated with this user
     *
     * @param userName Current user's ID
     * @return an array of Person object
     * @throws SQLException exception that can happen using SQL
     */
    public List<Person> getAllPerson(String userName) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Person> personList = new ArrayList<>();
        String sql = "select personID from person where descendant = ?";
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person temp = getPersonWithID(rs.getString("personID"));
                personList.add(temp);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return personList;
    }

    public void deletePerson(String descendant) //throws SQLException
    {
        String sql = "delete from person where descendant = ?";
        PreparedStatement stmt;
        try {
            stmt = connect.prepareStatement(sql);
            stmt.setString(1, descendant);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally
//        {
//            if(stmt != null)
//            {
//                stmt.close();
//            }
//        }

    }

}

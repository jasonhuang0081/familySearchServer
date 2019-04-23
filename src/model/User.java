package model;

/**
 * This class contains all the data members of a user
 */
public class User {
    /**
     * Unique user name
     */
    private String userName;
    /**
     * User’s password
     */
    private String password;
    /**
     * User’s email address
     */
    private String email;
    /**
     * User’s first name
     */
    private String firstName;
    /**
     * User’s last name
     */
    private String lastName;
    /**
     * User’s gender
     */
    private String gender;
    /**
     * Unique Person ID assigned to this user’s generated Person object
     */
    private String personID;

    /**
     * Constructor that takes in all data to generate the class
     *
     * @param userName  unique user name
     * @param password  User’s password
     * @param email     User’s email address
     * @param firstName User’s first name
     * @param lastName  User’s last name
     * @param gender    User’s gender
     * @param personID  Unique Person ID assigned to this user’s generated Person object
     */
    public User(String userName, String password, String email,
                String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * This compare all the data members to see if they are all equal
     *
     * @param o another User object
     * @return if two User object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender) &&
                personID.equals(user.personID);
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getGender() {
        return gender;
    }


    public String getPersonID() {
        return personID;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }
}

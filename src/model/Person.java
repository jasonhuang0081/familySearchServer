package model;

/**
 * This contains all the components in a person class
 */
public class Person {
    /**
     * Unique identifier for this person
     */
    private String personID;
    /**
     * Unique identifier for this person
     */
    private String descendant;
    /**
     * Person’s first name
     */
    private String firstName;
    /**
     * Person’s last name
     */
    private String lastName;
    /**
     * Person’s gender
     */
    private String gender;
    /**
     * ID of person’s father (possibly null)
     */
    private String father;
    /**
     * ID of person’s mother (possibly null)
     */
    private String mother;
    /**
     * ID of person’s spouse (possibly null)
     */
    private String spouse;


    /**
     * Constructor that takes in all data to generate the class
     *
     * @param personID   Unique identifier for this person
     * @param descendant Unique identifier for this person
     * @param firstName  Person’s first name
     * @param lastName   Person’s last name
     * @param gender     Person’s gender
     * @param father     ID of person’s father (possibly null)
     * @param mother     ID of person’s mother (possibly null)
     * @param spouse     ID of person’s spouse (possibly null)
     */
    public Person(String personID, String descendant, String firstName, String lastName,
                  String gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * This compare all the data members to see if they are all equal
     *
     * @param o another Person object
     * @return if two person object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) &&
                descendant.equals(person.descendant) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                father.equals(person.father) &&
                mother.equals(person.mother) &&
                spouse.equals(person.spouse);
    }


    public String getPersonID() {
        return personID;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public String getDescendant() {
        return descendant;
    }


    public void setDescendant(String descendant) {
        this.descendant = descendant;
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


    public String getFather() {
        return father;
    }


    public String getMother() {
        return mother;
    }


    public String getSpouse() {
        return spouse;
    }


}

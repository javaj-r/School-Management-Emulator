package model;

public class Person extends User {

    private String firstName;
    private String lastName;
    private String nationalCode;

    public String getFirstName() {
        return this.firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public Person setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        return this;
    }
}

package data.user;

import constant.Permit;
import model.Person;
import util.GenericList;

import static constant.Permit.*;

public class Employee extends Person {

    private static final GenericList<Permit> EMPLOYEE_PERMITTs = new GenericList<>(
             CREATE_STUDENT
            , DELETE_STUDENT
            , READ_STUDENT
            , UPDATE_STUDENT

            , CREATE_PROFESSOR
            , DELETE_PROFESSOR
            , READ_PROFESSOR
            , UPDATE_PROFESSOR

            , CREATE_EMPLOYEE
            , DELETE_EMPLOYEE
            , READ_EMPLOYEE
            , UPDATE_EMPLOYEE

            , CREATE_COURSE
            , DELETE_COURSE
            , READ_COURSE
            , UPDATE_COURSE
    );

    public Employee() {
        super();
        super.setPermissions(EMPLOYEE_PERMITTs);
    }

    private Long salary;

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "[ username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstName() +
                ", lastName:" + super.getLastName() +
                ", nationalCode:" + super.getNationalCode() +
                ", salary:" + getSalary() +
                " ]";

    }
}

package control.controller;

import model.Person;

/**
 * @author javid
 * Created on 12/29/2021
 */
public class PersonController {

    static void setParameters(Person person, String firstName, String lastName, String nationalCode) {
        person.setFirstName(firstName)
                .setLastName(lastName)
                .setNationalCode(nationalCode);
    }

    static void updateParameters(Person person, String firstName, String lastName, String nationalCode) {
        if (Controller.isForUpdate(firstName))
            person.setFirstName(firstName);

        if (Controller.isForUpdate(lastName))
            person.setLastName(lastName);

        if (Controller.isForUpdate(nationalCode))
            person.setNationalCode(nationalCode);
    }
}

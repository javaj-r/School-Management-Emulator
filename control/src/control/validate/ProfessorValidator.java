package control.validate;

import constant.Permit;

import java.util.Map;

import static constant.Permit.*;

/**
 * @author javid
 * Created on 12/28/2021
 */
public class ProfessorValidator extends Validator {

    private static final Map<String, Permit> PERMISSION_MAP = Map.of(
            "CREATE-PROFESSOR", CREATE_PROFESSOR,
            "REGISTER-PROFESSOR", CREATE_PROFESSOR,

            "READ-PROFESSOR", READ_PROFESSOR,
            "GET-PROFESSOR", READ_PROFESSOR,

            "UPDATE-PROFESSOR", UPDATE_PROFESSOR,
            "EDIT-PROFESSOR", UPDATE_PROFESSOR,

            "DELETE-PROFESSOR", DELETE_PROFESSOR,
            "UNREGISTER-PROFESSOR", DELETE_PROFESSOR,

            "ADD-PROFESSOR-COURSES", ADD_PROFESSOR_COURSES,
            "SALARY-PROFESSOR-I", SALARY_PROFESSOR_I
    );

    public ProfessorValidator() {
        super(PERMISSION_MAP);
    }
}

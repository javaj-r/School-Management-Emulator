package control.validate;

import constant.Permit;

import java.util.Map;

import static constant.Permit.*;

/**
 * @author javid
 * Created on 12/28/2021
 */
public class StudentValidator extends Validator {

    private static final Map<String, Permit> PERMISSION_MAP = Map.of(
            "CREATE-STUDENT", CREATE_STUDENT,
            "REGISTER-STUDENT", CREATE_STUDENT,

            "READ-STUDENT", READ_STUDENT,
            "GET-STUDENT", READ_STUDENT,

            "UPDATE-STUDENT", UPDATE_STUDENT,
            "EDIT-STUDENT", UPDATE_STUDENT,

            "DELETE-STUDENT", DELETE_STUDENT,
            "UNREGISTER-STUDENT", DELETE_STUDENT,

            "GET-STUDENT-I", GET_STUDENT_I,
            "SCORE-STUDENT", SCORE_STUDENT
    );

    public StudentValidator() {
        super(PERMISSION_MAP);
    }
}

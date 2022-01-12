package control.validate;

import constant.Permit;

import java.util.Map;

import static constant.Permit.*;

/**
 * @author javid
 * Created on 12/29/2021
 */
public class CourseValidator extends Validator {

    private static final Map<String, Permit> PERMISSION_MAP = Map.of(
            "CREATE-COURSE", CREATE_COURSE,
            "DELETE-COURSE", DELETE_COURSE,

            "READ-COURSE", READ_COURSE,
            "GET-COURSE", READ_COURSE,

            "UPDATE-COURSE", UPDATE_COURSE,
            "EDIT-COURSE", UPDATE_COURSE,

            "REGISTER-COURSE", REGISTER_COURSE
    );

    public CourseValidator() {
        super(PERMISSION_MAP);
    }
}

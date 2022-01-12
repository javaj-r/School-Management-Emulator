package control.validate;

import constant.Permit;

import java.util.Map;

import static constant.Permit.*;

/**
 * @author javid
 * Created on 12/27/2021
 */
public class EmployeeValidator extends Validator {

    private static final Map<String, Permit> PERMISSION_MAP = Map.of(
            "CREATE-EMPLOYEE", CREATE_EMPLOYEE,
            "REGISTER-EMPLOYEE", CREATE_EMPLOYEE,

            "READ-EMPLOYEE", READ_EMPLOYEE,
            "GET-EMPLOYEE", READ_EMPLOYEE,

            "UPDATE-EMPLOYEE", UPDATE_EMPLOYEE,
            "EDIT-EMPLOYEE", UPDATE_EMPLOYEE,

            "DELETE-EMPLOYEE", DELETE_EMPLOYEE,
            "UNREGISTER-EMPLOYEE", DELETE_EMPLOYEE
    );

    public EmployeeValidator() {
        super(PERMISSION_MAP);
    }
}

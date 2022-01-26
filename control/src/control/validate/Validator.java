package control.validate;

import constant.Permit;
import control.Request;
import control.exception.ValidationException;
import data.user.Root;
import model.User;

import java.util.Map;

/**
 * @author javid
 * Created on 12/27/2021
 */
public class Validator {

    private final Map<String, Permit> permissionMap;

    Validator(Map<String, Permit> permissions) {
        this.permissionMap = permissions;
    }

    public void isValid(User user, Request request) {

        if (isValid(user))
            return;

        Permit permit = permissionMap.get(request.getKeyWord());

        if (permit == null || user.getPermissions().contains(permit))
            return;

        throw new ValidationException("You are not permitted to this request!");
    }


    private boolean isValid(User user) {
        return user instanceof Root;
    }

    private Permit getPermissionByKey(String key) {
        try {
            return Permit.valueOf(key);
        } catch (Exception ignored) {
        }

        return null;
    }
}

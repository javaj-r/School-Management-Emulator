package control.validate;

import constant.Permit;
import control.Request;
import data.user.Root;
import model.User;

import java.util.Map;

/**
 * @author javid
 * Created on 12/27/2021
 */
public class Validator {

    private final Map<String, Permit> permission_map;

    Validator(Map<String, Permit> permissions) {
        this.permission_map = permissions;
    }

    public boolean isValid(User user, Request request) {

        if (isValid(user))
            return true;

        Permit permit = permission_map.get(request.getKeyWord());

        if (permit == null)
            return true;

        return user.getPermissions().contains(permit);
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

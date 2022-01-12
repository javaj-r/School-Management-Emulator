package control.controller;

import model.User;

/**
 * @author javid
 * Created on 12/29/2021
 */
public class UserController {

    static void setParameters(User user, String username, String password){
        user.setUsername(username).setPassword(password);
    }

    static void updatePassword(User user, String password) {
        if (Controller.isForUpdate(password))
            user.setPassword(password);
    }
}

package control.controller;

import control.Request;
import control.Response;
import control.validate.Validator;
import model.User;

/**
 * @author javid
 * Created on 12/28/2021
 */
public abstract class AbstractUserController {

    Validator validator;
    User user;

    public AbstractUserController(Validator validator) {
        this.validator = validator;
    }

    abstract Response create(Request request);

    abstract Response read(Request request);

    abstract Response readAll(Request request);

    abstract Response update(Request request);

    abstract Response delete(Request request);

    abstract Response read(User user, Request request);

}

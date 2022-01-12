package control.controller;

import control.Request;
import control.Response;
import control.repository.CourseRepository;
import control.repository.UserRepository;
import model.User;

/**
 * @author javid
 * Created on 12/25/2021
 */
public interface Controller {

    UserRepository USER_REPOSITORY = UserRepository.getInstance();
    CourseRepository COURSE_REPOSITORY = CourseRepository.getInstance();

    Response rout(User user, Request request);

    default Response getResponse(Request request) {
        return new Response().setSessionId(request.getSessionId());
    }

    static boolean isForUpdate(String command) {
        return !command.equals("-");
    }

}

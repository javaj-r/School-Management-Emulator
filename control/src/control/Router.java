package control;

import control.controller.*;
import control.repository.UserRepository;
import model.User;

import java.util.Random;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class Router {

    private UserRepository repository = UserRepository.getInstance();
    private Controller employeeController = new EmployeeController();
    private Controller studentController = new StudentController();
    private Controller professorController = new ProfessorController();
    private Controller courseController = new CourseController();

    private Session session;

    public Response request(Request request) {
        try {

            if (this.session == null) {
                if (request.getSessionId() == null) {
                    return login(request);
                } else {
                    return new Response().setBody("Session closed! login again.");
                }
            }

            if (this.session.id.equals(request.getSessionId()))
                return route(session.user, request);

            return new Response().setBody("You didn't login!");

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return null;
    }

    private Response route(User user, Request request) {
        try {

            String key = request.getKeyWord().trim().toUpperCase();
            request.setKeyWord(key);

            String switchKye = key.indexOf('-') == -1 ? key
                    : key.substring(key.indexOf('-') + 1);

            return switch (switchKye) {

                case "LOGOUT" -> {
                    this.session = null;
                    yield new Response().setBody("Logout success.");
                }

                case "EMPLOYEE"/*, "EMPLOYEE-I"*/ -> employeeController.rout(user, request);
                case "STUDENT", "STUDENT-I" -> studentController.rout(user, request);
                case "PROFESSOR", "PROFESSOR-COURSES", "PROFESSOR-I" -> professorController.rout(user, request);
                case "COURSE", "COURSE-MY" -> courseController.rout(user, request);

                default -> null;
            };

        } catch (Exception ignored) {
        }

        return null;
    }

    private Response login(Request request) {
        try {
            String username = request.getUsername();
            String password = request.getPassword();
            User user = this.repository.getUser(username, password);
            Response response = new Response();

            if (user == null) {
                return response.setBody("User not found!");
            } else {
                this.session = new Session(user);
                return response.setSessionId(this.session.id)
                        .setBody("Hi " + username + "!");
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static class Session {
        private int intId;
        private String id;
        private User user;

        public Session(User user) {
            this.user = user;
            intId = new Random().nextInt();
            id = Integer.toString(intId);
        }
    }

}

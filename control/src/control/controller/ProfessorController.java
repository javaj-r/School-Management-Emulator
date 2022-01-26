package control.controller;

import control.Request;
import control.Response;
import control.validate.ProfessorValidator;
import data.course.Course;
import data.user.Professor;
import model.User;
import util.GenericList;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class ProfessorController extends AbstractUserController implements Controller {

    public ProfessorController() {
        super(new ProfessorValidator());
    }

    @Override
    public Response rout(User user, Request request) {
        try {
            validator.isValid(user, request);
        } catch (Exception e) {
            return getResponse(request).setBody(e.getMessage());
        }

        String key = request.getKeyWord();
        try {
            return switch (key) {
                case "CREATE-PROFESSOR", "REGISTER-PROFESSOR" -> create(request);
                case "READ-PROFESSOR", "GET-PROFESSOR" -> read(request);
                case "UPDATE-PROFESSOR", "EDIT-PROFESSOR" -> update(request);
                case "DELETE-PROFESSOR", "UNREGISTER-PROFESSOR" -> delete(request);
                case "ADD-PROFESSOR-COURSES" -> addCourse(request);
                case "SALARY-PROFESSOR-I" -> getSalary(user, request);
                default -> null;
            };
        } catch (ArrayIndexOutOfBoundsException ignored) {
            getResponse(request).setBody("Invalid command!");
        }

        return null;
    }

    private Response getSalary(User user, Request request) {
//        ", salary:" + getSalary() +
        if (!(user instanceof Professor))
            return getResponse(request).setBody("Not Professor!");

        Professor professor = (Professor) user;
        int termNumber = Integer.parseInt(request.getBody().get(0));
        long salary = professor.getSalaryByTerm(termNumber);

        return getResponse(request).setBody(professor.toString() + " -> salary: " + salary);
    }

    private Response addCourse(Request request) {
        GenericList<String> commands = request.getBody();
        Professor professor = (Professor) USER_REPOSITORY.getByUsername(commands.get(0), Professor.class);
        if (professor == null)
            return getResponse(request).setBody("Professor Not exists!");


        Course course = COURSE_REPOSITORY.getByCourseName(commands.get(1));
        if (course == null)
            return getResponse(request).setBody("Course Not exists!");

        int termNumber = Integer.parseInt(commands.get(2));
        professor.getCourses().put(course, termNumber);

        return getResponse(request).setBody("Course added.");
    }

    @Override
    Response create(Request request) {
        GenericList<String> commands = request.getBody();

        Professor professor = new Professor();
        UserController.setParameters(professor, commands.get(0), commands.get(1));

        if (commands.size() >= 5) {
            PersonController.setParameters(professor
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));
        }

        if (commands.size() >= 6) {
            boolean b = commands.get(5).equalsIgnoreCase("Yes");
            professor.setFacultyMember(b);
        }

        if (USER_REPOSITORY.add(professor))
            return getResponse(request).setBody("Professor registered successfully.");

        return getResponse(request).setBody(professor.getUsername() + " already exists!");
    }

    @Override
    Response read(Request request) {
        GenericList<String> commands = request.getBody();
        if(user instanceof Professor && !commands.get(0).equalsIgnoreCase(user.getUsername())) {
            return getResponse(request).setBody("You can't access other professors information!");
        }

        if (commands.get(0).equalsIgnoreCase("ALL")) {
            return readAll(request);
        }

        Professor professor = (Professor) USER_REPOSITORY.getByUsername(commands.get(0), Professor.class);
        return getResponse(request).setBody(professor == null ? "Not exists!" : professor.toString());
    }

    @Override
    Response readAll(Request request) {
        StringBuilder builder = new StringBuilder();

        for (Professor professor : USER_REPOSITORY.getAllProfessors()) {
            builder.append(professor).append("\n");
        }

        return getResponse(request).setBody(builder.toString());
    }

    @Override
    Response update(Request request) {
        GenericList<String> commands = request.getBody();

        Professor professor = (Professor) USER_REPOSITORY.getByUsername(commands.get(0), Professor.class);
        if (professor != null) {

            UserController.updatePassword(professor, request.getBody().get(1));

            PersonController.updateParameters(professor
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));

            if (Controller.isForUpdate(commands.get(5))) {
                professor.setFacultyMember(commands.get(5).equalsIgnoreCase("Yes"));
            }

            return getResponse(request).setBody("professor updated successfully.");
        }

        return getResponse(request).setBody(commands.get(0) + " not found!");
    }

    @Override
    Response delete(Request request) {
        String professorUsername = request.getBody().get(0);
        if (USER_REPOSITORY.remove(professorUsername, Professor.class))
            return getResponse(request).setBody(professorUsername + " unregistered successfully.");

        return getResponse(request).setBody(professorUsername + " not exists!");
    }

    @Override
    Response read(User user, Request request) {
        return null;
    }
}

package control.controller;

import control.Request;
import control.Response;
import control.validate.CourseValidator;
import data.course.Course;
import data.user.Student;
import model.User;
import util.GenericList;

/**
 * @author javid
 * Created on 12/29/2021
 */
public class CourseController extends AbstractUserController implements Controller {

    public CourseController() {
        super(new CourseValidator());
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
                case "CREATE-COURSE" -> create(request);
                case "READ-COURSE", "GET-COURSE" -> read(request);
                case "UPDATE-COURSE", "EDIT-COURSE" -> update(request);
                case "DELETE-COURSE" -> delete(request);
                case "REGISTER-COURSE" -> register(user, request);
                default -> null;
            };
        } catch (ArrayIndexOutOfBoundsException ignored) {
            getResponse(request).setBody("Invalid command!");
        }

        return null;
    }

    @Override
    Response create(Request request) {
        GenericList<String> commands = request.getBody();

        Course course = new Course(commands.get(0), Integer.valueOf(commands.get(1)));

        if (commands.size() > 2) {
            Course requiredCourse = COURSE_REPOSITORY.getByCourseName(commands.get(2));
            if (requiredCourse == null)
                getResponse(request).setBody("Failed: required course not exists!");

            course.setRequiredCourse(requiredCourse);
        }

        if (COURSE_REPOSITORY.add(course))
            return getResponse(request).setBody("Course created successfully.");

        return getResponse(request).setBody(course.getName() + " already exists!");
    }

    @Override
    Response read(Request request) {
        GenericList<String> commands = request.getBody();

        if (commands.get(0).equalsIgnoreCase("ALL")) {
            return readAll(request);
        }

        Course course = COURSE_REPOSITORY.getByCourseName(commands.get(0));
        return getResponse(request).setBody(course == null ? "Not exists!" : course.toString());
    }

    @Override
    Response readAll(Request request) {
        StringBuilder builder = new StringBuilder();

        for (Course course : COURSE_REPOSITORY.toArray()) {
            builder.append(course).append("\n");
        }

        return getResponse(request).setBody(builder.toString());
    }

    @Override
    Response update(Request request) {
        GenericList<String> commands = request.getBody();

        Course course = COURSE_REPOSITORY.getByCourseName(commands.get(0));
        if (course != null) {

            String unit = commands.get(1);
            if (Controller.isForUpdate(unit)) {
                course.setUnit(Integer.valueOf(unit));
            }

            String name = commands.get(2);
            if (Controller.isForUpdate(name)) {
                Course requiredCourse = COURSE_REPOSITORY.getByCourseName(name);
                if (requiredCourse == null)
                    getResponse(request).setBody("Failed: required course not exists!");

                course.setRequiredCourse(requiredCourse);
            }

            return getResponse(request).setBody("Course updated successfully.");
        }

        return getResponse(request).setBody(commands.get(0) + " not found!");
    }

    @Override
    Response delete(Request request) {
        GenericList<String> commands = request.getBody();
        Course course = COURSE_REPOSITORY.getByCourseName(commands.get(0));
        if (course != null) {
            if (commands.size() > 1 && commands.get(1).equalsIgnoreCase("required")) {
                course.setRequiredCourse(null);

                return getResponse(request).setBody("Required course deleted successfully.");
            }

            COURSE_REPOSITORY.remove(COURSE_REPOSITORY.indexOf(course));

            return getResponse(request).setBody("Course deleted successfully.");
        }

        return getResponse(request).setBody(commands.get(0) + " not exists!");
    }

    @Override
    Response read(User user, Request request) {
        // Unused for this class
        return null;
    }

    Response register(User user, Request request) {
        if (!(user instanceof Student))
            return getResponse(request).setBody("Not student!");

        Student student = (Student) user;

        String courseName = request.getBody().get(0);
        Course course = COURSE_REPOSITORY.getByCourseName(courseName);
        if (course == null) {
            return getResponse(request).setBody("Course not exists!");
        }

        int units = student.getCurrentUnits() + course.getUnit();
        if (units > student.getUnitsLimit())
            return getResponse(request).setBody("Out of units limit!");

        if (course.getRequiredCourse() == null || student.isPassed(course.getRequiredCourse())) {
            course.getStudents().add(student);
            student.getCourses().put(course, null);
            return getResponse(request).setBody("You registered successfully.");
        }

        String requiredCourse = course.getRequiredCourse() != null ? course.getRequiredCourse().getName() : "";
        return getResponse(request).setBody("You did not passed " + requiredCourse);
    }
}

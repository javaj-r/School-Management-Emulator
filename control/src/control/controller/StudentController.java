package control.controller;

import control.Request;
import control.Response;
import control.validate.StudentValidator;
import data.course.Course;
import data.user.Employee;
import data.user.Root;
import data.user.Student;
import model.User;
import util.GenericList;

import java.util.Map;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class StudentController extends AbstractUserController implements Controller {

    private static int lastStudentCode = 0;

    public StudentController() {
        super(new StudentValidator());

    }

    @Override
    public Response rout(User user, Request request) {

        this.user = user;
        try {
            validator.isValid(user, request);
        } catch (Exception e) {
            return getResponse(request).setBody(e.getMessage());
        }


        String key = request.getKeyWord();
        try {
            return switch (key) {
                case "CREATE-STUDENT", "REGISTER-STUDENT" -> create(request);
                case "READ-STUDENT", "GET-STUDENT" -> read(request);
                case "UPDATE-STUDENT", "EDIT-STUDENT" -> update(request);
                case "DELETE-STUDENT", "UNREGISTER-STUDENT" -> delete(request);
                case "GET-STUDENT-I" -> read(user, request);
                case "SCORE-STUDENT" -> scoreStudent(request);
                default -> null;
            };

        } catch (ArrayIndexOutOfBoundsException ignored) {
            getResponse(request).setBody("Invalid command!");
        }

        return null;
    }

    private Response scoreStudent(Request request) {
        GenericList<String> commands = request.getBody();

        Student student = (Student) USER_REPOSITORY.getByUsername(commands.get(0), Student.class);
        if (student == null)
            return getResponse(request).setBody("Student Not exists!");

        Course course = COURSE_REPOSITORY.getByCourseName(commands.get(1));
        if (course == null)
            return getResponse(request).setBody("Course Not exists!");

        Map<Course, Integer> courses = student.getCourses();
        Map<Course, Integer> passedCourses = student.getCourses();
        int score = Integer.valueOf(commands.get(2));

        if (score < 0 || score > 20)
            return getResponse(request).setBody("Score is not valid!");

        if (courses.containsKey(course)) {
            courses.remove(course);
            passedCourses.put(course, score);
            return getResponse(request).setBody("Process success!");
        }

        return null;
    }

    private static int getLastStudentCode() {
        return ++lastStudentCode;
    }

    @Override
    Response create(Request request) {
        if (!(Root.class.isInstance(user) || Employee.class.isInstance(user)))
            return null;


        GenericList<String> commands = request.getBody();

        Student student = new Student();
        UserController.setParameters(student
                , commands.get(0)
                , commands.get(1));

        if (commands.size() >= 5) {
            PersonController.setParameters(student
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));
            student.setStudentCode(getLastStudentCode());
        }

        if (USER_REPOSITORY.add(student))
            return getResponse(request).setBody("Student registered successfully.");

        return getResponse(request).setBody(student.getUsername() + " already exists!");
    }

    @Override
    Response read(Request request) {
        if (!(Root.class.isInstance(user) || Employee.class.isInstance(user)))
            return null;

        GenericList<String> commands = request.getBody();

        if (commands.get(0).equalsIgnoreCase("ALL")) {
            return readAll(request);
        }

        Student student = (Student) USER_REPOSITORY.getByUsername(commands.get(0), Student.class);
        return getResponse(request).setBody(student == null ? "Not exists!" : student.toString());
    }

    @Override
    Response readAll(Request request) {

        StringBuilder builder = new StringBuilder();

        for (Student student : USER_REPOSITORY.getAllStudents()) {
            builder.append(student).append("\n");
        }

        return getResponse(request).setBody(builder.toString());
    }

    @Override
    Response update(Request request) {
        GenericList<String> commands = request.getBody();

        Student student = (Student) USER_REPOSITORY.getByUsername(commands.get(0), Student.class);
        if (student != null) {

            UserController.updatePassword(student, request.getBody().get(1));

            PersonController.updateParameters(student
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));

            return getResponse(request).setBody("Student updated successfully.");
        }

        return getResponse(request).setBody(request.getBody().get(0) + " not found!");
    }

    @Override
    Response delete(Request request) {
        String studentUsername = request.getBody().get(0);
        if (USER_REPOSITORY.remove(studentUsername, Student.class))
            return getResponse(request).setBody(studentUsername + " unregistered successfully.");

        return getResponse(request).setBody(studentUsername + " not exists!");
    }

    @Override
    Response read(User user, Request request) {
        if (!(user instanceof Student))
            return getResponse(request).setBody("Not student!");

        Student student = (Student) user;

        String command = request.getBody().get(0);
        if (command.equalsIgnoreCase("INFO")) {
            return getResponse(request).setBody(student.toString());
        }

        if (command.equalsIgnoreCase("ALL")) {
            StringBuilder builder = new StringBuilder(student.toString()).append("\n");

            builder.append("\n").append("Current courses:").append("\n");
            termToString(student.getCourses(), builder);

            builder.append("\n").append("Passed courses:").append("\n");
            termToString(student.getLastTermCourses(), builder);

            return getResponse(request).setBody(builder.toString());
        }

        return null;
    }

    private void termToString(Map<Course, Integer> courses, StringBuilder builder) {
        if (courses.size() > 0) {
            courses.forEach((course, score) ->
                    builder.append(course.toString())
                            .append(" score:")
                            .append(score == null ? "-" : score));
        } else {
            builder.append("[]");
        }
    }

}

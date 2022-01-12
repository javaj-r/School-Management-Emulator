package control.controller;

import control.Request;
import control.Response;
import control.validate.EmployeeValidator;
import data.user.Employee;
import model.User;
import util.GenericList;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class EmployeeController extends AbstractUserController implements Controller {

    public EmployeeController() {
        super(new EmployeeValidator());
    }

    @Override
    public Response rout(User user, Request request) {
        if (!validator.isValid(user, request)) {
            return getResponse(request)
                    .setBody("You are not permitted to this request!");
        }

        String key = request.getKeyWord();
        try {
            return switch (key) {
                case "CREATE-EMPLOYEE", "REGISTER-EMPLOYEE" -> create(request);
                case "READ-EMPLOYEE", "GET-EMPLOYEE" -> read(request);
                case "UPDATE-EMPLOYEE", "EDIT-EMPLOYEE" -> update(request);
                case "DELETE-EMPLOYEE", "UNREGISTER-EMPLOYEE" -> delete(request);
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

        Employee employee = new Employee();
        UserController.setParameters(employee, commands.get(0), commands.get(1));

        if (commands.size() >= 6) {
            PersonController.setParameters(employee
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));
            employee.setSalary(Long.valueOf(commands.get(5)));
        }

        if (USER_REPOSITORY.add(employee))
            return getResponse(request).setBody("Employee registered successfully.");

        return getResponse(request).setBody(employee.getUsername() + " already exists!");
    }

    @Override
    Response read(Request request) {
        GenericList<String> commands = request.getBody();

        if (commands.get(0).equalsIgnoreCase("ALL")) {
            return readAll(request);
        }

        Employee employee = (Employee) USER_REPOSITORY.getByUsername(commands.get(0), Employee.class);
        return getResponse(request).setBody(employee == null ? "Not exists!" : employee.toString());
    }

    @Override
    Response readAll(Request request) {
        StringBuilder builder = new StringBuilder();

        for (Employee employee : USER_REPOSITORY.getAllEmployees()) {
            builder.append(employee).append("\n");
        }

        return getResponse(request).setBody(builder.toString());
    }

    @Override
    Response update(Request request) {
        GenericList<String> commands = request.getBody();

        Employee employee = (Employee) USER_REPOSITORY.getByUsername(commands.get(0), Employee.class);
        if (employee != null) {

            UserController.updatePassword(employee, request.getBody().get(1));

            PersonController.updateParameters(employee
                    , commands.get(2)
                    , commands.get(3)
                    , commands.get(4));

            employee.setSalary(Long.valueOf(commands.get(5)));

            return getResponse(request).setBody("Employee updated successfully.");
        }

        return getResponse(request).setBody(request.getBody().get(0) + " not found!");
    }

    @Override
    Response delete(Request request) {
        String employeeUsername = request.getBody().get(0);
        if (USER_REPOSITORY.remove(employeeUsername, Employee.class))
            return getResponse(request).setBody(employeeUsername + " unregistered successfully.");

        return getResponse(request).setBody(employeeUsername + " not exists!");
    }

    @Override
    Response read(User user, Request request) {
        return null;
    }

}

package control.repository;

import data.user.Employee;
import data.user.Professor;
import data.user.Root;
import data.user.Student;
import model.User;
import util.GenericList;

import java.util.Arrays;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class UserRepository extends GenericList<User> {

    private static int lastId = 0;

    private UserRepository() {
        super(Root.getInstance());
    }

    private static class Singleton {
        private static final UserRepository INSTANCE = new UserRepository();
    }

    public static UserRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public static int getLastId() {
        return lastId;
    }

    private static int getCurrentId() {
        return ++lastId;
    }

    @Override
    public boolean add(User user) {
        if (user.isNew() && isUserNameValid(user.getUsername())) {
            user.setId(getCurrentId());
            return super.add(user);
        }

        return false;
    }

    public boolean remove(String username, Class<? extends User> type) {
        for (int i = 0; i < size(); i++) {
            User user = super.get(i);
            if (type.isInstance(user) && user.getUsername().equals(username)) {
                super.remove(i);
                return true;
            }
        }

        return false;
    }

    public User getByUsername(String username, Class<? extends User> type){
        return Arrays.stream(super.toArray())
                .filter(type::isInstance)
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Student[] getAllStudents() {
        return Arrays.stream(toArray())
                .filter(Student.class::isInstance)
                .toArray(Student[]::new);
    }

    public Employee[] getAllEmployees() {
        return Arrays.stream(toArray())
                .filter(user -> user.getClass().equals(Employee.class))
                .toArray(Employee[]::new);
    }

    public Professor[] getAllProfessors() {
        return Arrays.stream(toArray())
                .filter(Professor.class::isInstance)
                .toArray(Professor[]::new);
    }

    private boolean isUserNameValid(String username) {
        for (int i = 0; i < size(); i++) {
            User user = super.get(i);
            if (user.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public User getUser(String username, String password) {
        for (int i = 0; i < size(); i++) {
            User user = super.get(i);
            boolean equalsUsername = user.getUsername().equals(username);
            boolean equalsPassword = user.getPassword().equals(password);
            if (equalsUsername && equalsPassword)
                return user;
        }

        return null;
    }
}

package data.user;

import constant.Permit;
import data.course.Course;
import model.Person;
import util.GenericList;

import java.util.HashMap;
import java.util.Map;

import static constant.Permit.*;

public class Student extends Person {

    private static final GenericList<Permit> STUDENT_PERMITTs = new GenericList<>(
            GET_STUDENT_I
            , READ_COURSE
            , REGISTER_COURSE
    );

    private int studentCode;
    private Map<Course, Integer> courses = new HashMap<>();
    private Map<Course, Integer> lastTermCourses = new HashMap<>();

    public Student() {
        super();
        super.setPermissions(STUDENT_PERMITTs);
    }

    public Map<Course, Integer> getCourses() {
        return courses;
    }

    public void setCourses(Map<Course, Integer> courses) {
        this.courses = courses;
    }

    public Map<Course, Integer> getLastTermCourses() {
        return lastTermCourses;
    }

    public void setLastTermCourses(Map<Course, Integer> lastTermCourses) {
        this.lastTermCourses = lastTermCourses;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public int getLastTermAVG() {
        int sum = 0;
        for (Integer score : lastTermCourses.values()) {
            sum += score;
        }
        return sum / lastTermCourses.size();
    }

    public int getCurrentUnits() {
        int sum = 0;
        for (Course course : courses.keySet()) {
            sum += course.getUnit();
        }
        return sum;
    }

    public int getUnitsLimit() {
        if (this.lastTermCourses.size() == 0 || getLastTermAVG() >= 18) {
            return 24;
        }

        return 20;
    }

    public boolean isPassed(Course course) {
        return lastTermCourses.keySet().contains(course);
    }


    @Override
    public String toString() {
        return "[ studentCode:" + getStudentCode() +
                ", username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstName() +
                ", lastName:" + super.getLastName() +
                ", nationalCode:" + super.getNationalCode() +
                " ]";
    }

}

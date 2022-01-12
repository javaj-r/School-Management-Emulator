package data.user;

import constant.Permit;
import data.course.Course;
import util.GenericList;

import java.util.HashMap;

import static constant.Permit.*;

public class Professor extends Employee {

    private static final GenericList<Permit> PROFESSOR_PERMITTs = new GenericList<>(
            READ_PROFESSOR
            , SCORE_STUDENT
            , SALARY_PROFESSOR_I
    );

    private static long salaryPerCourse = 1_000_000L;
    private static long facultyMemberFee = 5_000_000L;
    private HashMap<Course, Integer> courses = new HashMap<>();
    private boolean facultyMember;

    public Professor() {
        super();
        super.setPermissions(PROFESSOR_PERMITTs);
    }

    public HashMap<Course, Integer> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<Course, Integer> courses) {
        this.courses = courses;
    }

    public static void setSalaryPerCourse(long salaryPerCourse) {
        Professor.salaryPerCourse = salaryPerCourse;
    }

    public static long getSalaryPerCourse() {
        return salaryPerCourse;
    }

    public boolean isFacultyMember() {
        return facultyMember;
    }

    public void setFacultyMember(boolean facultyMember) {
        this.facultyMember = facultyMember;
    }

    public static long getFacultyMemberFee() {
        return facultyMemberFee;
    }

    public static void setFacultyMemberFee(long facultyMemberFee) {
        Professor.facultyMemberFee = facultyMemberFee;
    }

    public Long getSalaryByTerm(int termNumber) {
        int[] sum = new int[1];

        courses.forEach((course, term) -> {
            if (term == termNumber)
                sum[0] += course.getUnit();
        });

        return sum[0] * salaryPerCourse
                + (isFacultyMember() ? facultyMemberFee : 0);
    }

    @Override
    public String toString() {
        return "[ username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstName() +
                ", lastName:" + super.getLastName() +
                ", nationalCode:" + super.getNationalCode() +
                ", isFacultyMember:" + (isFacultyMember() ? "Yes" : "No") +
                " ]";
    }
}

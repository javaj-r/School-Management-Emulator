package data.course;

import data.user.Student;
import model.NamedEntity;
import util.GenericList;

public class Course extends NamedEntity {

    private GenericList<Student> students = new GenericList<>();
    private Course requiredCourse;
    private Integer unit;


    public Course(String name, Integer unit) {
        super.setName(name);
        this.unit = unit;
    }

    public Course getRequiredCourse() {
        return requiredCourse;
    }

    public void setRequiredCourse(Course requiredCourse) {
        this.requiredCourse = requiredCourse;
    }

    public GenericList<Student> getStudents() {
        return students;
    }

    public void setStudents(GenericList<Student> students) {
        this.students = students;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "[ codeName:" + getName() +
                ", unit:" + unit +
                ", requiredCourse:" + (requiredCourse == null ? null : requiredCourse.getName()) +
                " ]";
    }
}

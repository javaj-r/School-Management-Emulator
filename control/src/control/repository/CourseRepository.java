package control.repository;

import data.course.Course;
import util.GenericList;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author javid
 * Created on 12/25/2021
 */
public class CourseRepository extends GenericList<Course> {

    private static int lastId = 0;

    private CourseRepository() {
    }

    private static class Singleton {
        private static final CourseRepository INSTANCE = new CourseRepository();
    }

    public static CourseRepository getInstance() {
        return Singleton.INSTANCE;
    }


    public Course getByCourseName(String codeName){
        Optional<Course> optional = Arrays.stream(toArray())
                .filter(course -> course.getName().equalsIgnoreCase(codeName))
                .findFirst();

        if(optional.isPresent())
            return optional.get();

        return null;
    }

    public static int getLastId() {
        return lastId;
    }

    private static int getCurrentId() {
        return ++lastId;
    }

    @Override
    public boolean add(Course course) {
        if (course.isNew() && getByCourseName(course.getName()) == null) {
            course.setId(getCurrentId());
            return super.add(course);
        }

        return false;
    }


}

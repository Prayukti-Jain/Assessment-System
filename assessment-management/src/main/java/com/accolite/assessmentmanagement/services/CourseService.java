package com.accolite.assessmentmanagement.services;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseService(UserService userService, CourseRepository courseRepository){
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public List<Course> getAllCourses(){
        Logger.getLogger("CourseService").info("[DB OP] getAllCourses");
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

//    Since CrudRepository handles save and edit, both in save, hence only one method for same
    @Transactional
    public Course saveCourse(Course course){
    	Logger.getLogger("CourseService").info("[DB OP] SaveCourse");
        return courseRepository.save(course);
    }

    @Transactional
    public Course getCourseById(Long courseId){
    	Logger.getLogger("CourseService").info("[DB OP] getCourseById");
//        Further tests needed to check for invalid id calls
        return courseRepository.findById(courseId).get();
    }

    @Transactional
    public void deleteCourseById(Long courseId){
    	Logger.getLogger("CourseService").info("[DB OP] deleteCourseById");
        courseRepository.deleteById(courseId);
    }

    public Course addUserSaveCourse(User user, Course course){
    	Logger.getLogger("CourseService").info("[-- OP] addUSerSaveCourse");
        course.setUser(user);
        return this.saveCourse(course);
    }

    public Course addUserByIdSaveCourse(String userId, Course course){
    	Logger.getLogger("CourseService").info("[-- OP] addUSerByIdSaveCourse");
        User user = this.userService.getUserById(userId);
        return addUserSaveCourse(user, course);
    }

    public Course checkUserByIdSaveCourse(String userId, Course course) throws UnAuthorizedAccessException {
    	Logger.getLogger("CourseService").info("[-- OP] checkUserByIdSaveCourse");
        if(!course.getUser().getId().equals(userId)) throw new UnAuthorizedAccessException("User does not have access to this Course");
        return this.saveCourse(course);
    }

    public void checkUserByIdDeleteCourseById(String userId, Long courseId) throws UnAuthorizedAccessException{
    	Logger.getLogger("CourseService").info("[-- OP] checkUserByIdDeleteCourseById");
        Course course = this.getCourseById(courseId);
        if(!course.getUser().getId().equals(userId)) throw new UnAuthorizedAccessException("User does not have access to this Course");
        this.deleteCourseById(courseId);
    }
}

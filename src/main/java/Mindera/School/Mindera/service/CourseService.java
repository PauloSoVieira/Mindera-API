package Mindera.School.Mindera.service;


import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
import Mindera.School.Mindera.exception.CourseException;
import Mindera.School.Mindera.mapper.CourseMapper;
import Mindera.School.Mindera.model.Course;
import Mindera.School.Mindera.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static Mindera.School.Mindera.message.Message.COURSE_CANNOT_BE_FOUND;
import static Mindera.School.Mindera.message.Message.COURSE_NOT_FOUND;

@Service

public class CourseService {


    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getAll() {

        List<Course> courses = courseRepository.findAll();
        return CourseMapper.INSTANCE.courseToCoursesDto(courses);

    }

    public CourseDto findByIdDto(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseException(COURSE_NOT_FOUND));
        return CourseMapper.INSTANCE.toDto(course);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseException(COURSE_NOT_FOUND));
    }

    public CourseDto addNewCourse(CourseCreationDto courseDto) {
        Validation(courseDto);
        Course course = CourseMapper.INSTANCE.toModelCreate(courseDto);
        courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);

    }

    public void Validation(CourseCreationDto courseDto) {
        if (courseDto.getName() == null || courseDto.getName().isEmpty()) {
            throw new CourseException(COURSE_CANNOT_BE_FOUND);
        }
    }

    public void deleteCourse(Long id) {
        if (courseRepository.findById(id).isPresent()) {
            courseRepository.deleteById(id);
        } else {
            throw new CourseException(COURSE_CANNOT_BE_FOUND);
        }

    }

    public CourseDto updateCourse(CourseDto courseDto, Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseException(COURSE_CANNOT_BE_FOUND));
        Course updateCourse = CourseMapper.INSTANCE.toModel(courseDto);
        course.setName(updateCourse.getName());
        course.setStudents(updateCourse.getStudents());
        courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);
    }
}

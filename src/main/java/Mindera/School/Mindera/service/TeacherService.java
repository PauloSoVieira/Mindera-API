package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.TeacherCreateDto;
import Mindera.School.Mindera.dto.TeacherDto;
import Mindera.School.Mindera.exception.CourseException;
import Mindera.School.Mindera.exception.TeacherException;
import Mindera.School.Mindera.mapper.TeacherMapper;
import Mindera.School.Mindera.model.Course;
import Mindera.School.Mindera.model.Teacher;
import Mindera.School.Mindera.repository.CourseRepository;
import Mindera.School.Mindera.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static Mindera.School.Mindera.message.Message.*;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public List<TeacherDto> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return TeacherMapper.INSTANCE.teacherToCoursesDto(teachers);
    }

    public TeacherDto getTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherException(TEACHER_CANNOT_BE_FOUND));
        return TeacherMapper.INSTANCE.toDto(teacher);

    }

    public TeacherDto addTeacher(TeacherCreateDto teacherDto) {
        Validation(teacherDto);
        Teacher teacher = TeacherMapper.INSTANCE.toModelCreate(teacherDto);
        teacherRepository.save(teacher);
        return TeacherMapper.INSTANCE.toDto(teacher);
    }

    public void Validation(TeacherCreateDto teacherDto) {
        if (teacherDto.getName() == null || teacherDto.getName().isEmpty()) {
            throw new TeacherException(TEACHER_CANT_BE_NULL);
        }
    }


    public void deleteById(Long id) {
        if (teacherRepository.findById(id).isPresent()) {
            teacherRepository.deleteById(id);
        } else {
            throw new TeacherException(TEACHER_CANNOT_BE_FOUND);
        }
    }

    public void updateTeacher(Long id, TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherException(TEACHER_CANNOT_BE_FOUND));
        Teacher updateTeacher = TeacherMapper.INSTANCE.toModel(teacherDto);
        updateTeacher.setId(id);
        teacher.setName(updateTeacher.getName());
        teacherRepository.save(teacher);
        TeacherMapper.INSTANCE.toDto(updateTeacher);
    }

    public TeacherDto addTeacherToCourse(Long id, Long courseid) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherException(TEACHER_CANNOT_BE_FOUND));
        Course course = courseRepository.findById(courseid).orElseThrow(() -> new CourseException(COURSE_CANNOT_BE_FOUND));
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);
        return TeacherMapper.INSTANCE.toDto(teacher);
    }


}

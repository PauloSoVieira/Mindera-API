package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import Mindera.School.Mindera.exception.StudentException;
import Mindera.School.Mindera.mapper.StudentMapper;
import Mindera.School.Mindera.model.Course;
import Mindera.School.Mindera.model.Student;
import Mindera.School.Mindera.repository.CourseRepository;
import Mindera.School.Mindera.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static Mindera.School.Mindera.message.Message.STUDENT_CANNOT_BE_FOUND;
import static Mindera.School.Mindera.message.Message.STUDENT_CANT_BE_NULL;

@Service
public class StudentService {


    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private CourseService courseService;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentMapper.INSTANCE.studentsToStudentsDto(students);
    }


    public StudentDto addStudent(StudentCreationDto studentDto) {

        validation(studentDto);
        Student student = StudentMapper.INSTANCE.toModelCreating(studentDto);
        studentRepository.save(student);

        return StudentMapper.INSTANCE.toDto(student);


    }

    public void validation(StudentCreationDto studentDto) {
        if (studentDto.getName() == null || studentDto.getName().isEmpty()) {
            throw new StudentException(STUDENT_CANT_BE_NULL);
        }
        if (studentDto.getName().matches("[0-9]+") && studentDto.getName().length() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(STUDENT_CANNOT_BE_FOUND));
        return StudentMapper.INSTANCE.toDto(student);

    }

    public void deleteStudent(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
        } else {
            throw new StudentException(STUDENT_CANNOT_BE_FOUND);
        }
    }

    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(STUDENT_CANNOT_BE_FOUND));
        Student updateStudent = StudentMapper.INSTANCE.toModel(studentDto);
        student.setName(updateStudent.getName());
        studentRepository.save(student);
        return StudentMapper.INSTANCE.toDto(student);

    }


    public StudentDto addCourse(Long id, Long courseId) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(STUDENT_CANNOT_BE_FOUND));
        Course course = courseService.findById(courseId);
        student.getCourses().add(course);
        student = studentRepository.save(student);
        StudentDto studentDto = StudentMapper.INSTANCE.toDto(student);
        return studentDto;
    }

    public void deleteAllStudents() {


        studentRepository.deleteAll();
        Integer deleted = getAllStudents().size();
        logger.info("Deleted {} students", deleted);
    }
}

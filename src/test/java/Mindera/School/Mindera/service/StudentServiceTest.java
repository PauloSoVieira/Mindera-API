package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import Mindera.School.Mindera.exception.StudentException;
import Mindera.School.Mindera.mapper.StudentMapper;
import Mindera.School.Mindera.model.Course;
import Mindera.School.Mindera.model.Student;
import Mindera.School.Mindera.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    List<Course> courses = new ArrayList<>(List.of(
            new Course(1L, "Java"),
            new Course(2L, "C++")
    ));
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private StudentService studentService;

    private static Student makeStudent(Long id, String name, List<Course> courses) {
        Student student = new Student(id, courses, name);
        student.setId(id);
        student.setName(name);
        student.setCourses(courses);
        return student;
    }

    @Test
    void add_Student_succeed() {

        StudentCreationDto student = new StudentCreationDto(1L, "Pablo", courses);

        studentService.addStudent(student);

        Student student1 = StudentMapper.INSTANCE.toModelCreating(student);

        verify(studentRepository, times(1)).save(student1);

    }

    @Test
    void add_Student_fail() {
        StudentCreationDto student = new StudentCreationDto(1L, "", courses);
        //studentService.addStudent(student);
        Student student1 = StudentMapper.INSTANCE.toModelCreating(student);
        //
        assertThrows(StudentException.class, () -> studentService.addStudent(student));
        verify(studentRepository, times(0)).save(student1);
    }

    @Test
    void validation_succeed() {
        StudentCreationDto student = new StudentCreationDto(1L, "Pablo", courses);
        studentService.validation(student);

    }

    @Test
    void get_StudentById_succeed() {

        Student student = new Student(1L, courses, "Pablo");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        StudentMapper.INSTANCE.toDto(student);

        studentService.getStudentById(1L);

        verify(studentRepository, times(1)).findById(1L);
        assertEquals(student.getId(), 1L);

    }

    @Test
    void delete_Student_succeed() {
        Student student = new Student(1L, courses, "Pablo");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void update_Student_succeed() {

        Student student = new Student(1L, courses, "Pablo");
        String expectedName = " Genius Pablo";

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        StudentDto st = StudentMapper.INSTANCE.toDto(student);
        st.setName(expectedName);
        studentService.updateStudent(st, 1L);

        verify(studentRepository, times(1)).save(student);
        assertEquals(expectedName, student.getName());
    }

    @Test
    void add_Course_succeed() {
        Student student = new Student(1L, courses, "Pablo");
        Course course = new Course(1L, "Java");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseService.findById(1L)).thenReturn(course);

        studentService.addCourse(1L, 1L);

        verify(studentRepository, times(1)).save(student);
        assertTrue(student.getCourses().contains(course));

    }


}
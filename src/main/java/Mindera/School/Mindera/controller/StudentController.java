package Mindera.School.Mindera.controller;


import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import Mindera.School.Mindera.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<StudentDto> addStudent(
            @Valid
            @RequestBody StudentCreationDto studentDto) {
        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.OK);

    }

    /********************************** Add student to course ***********************************/

    @PutMapping("/{id}/course/{courseId}")
    public ResponseEntity<StudentDto> addStudentToCourse(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(studentService.addCourse(id, courseId), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable Long id) {
        StudentDto dto = studentService.updateStudent(studentDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}

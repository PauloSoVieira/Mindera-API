package Mindera.School.Mindera.controller;


import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import Mindera.School.Mindera.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all students", description = "Returns all students in database")
    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }


    @Operation(summary = "Add new student", description = "Adds new student")
    @PostMapping("/")
    public ResponseEntity<StudentDto> addStudent(
            @Valid
            @RequestBody StudentCreationDto studentDto) {
        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.OK);

    }

    /********************************** Add student to course ***********************************/

    @Operation(summary = "Add student to course", description = "Adds student to course")
    @PutMapping("/{id}/course/{courseId}")
    public ResponseEntity<StudentDto> addStudentToCourse(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(studentService.addCourse(id, courseId), HttpStatus.OK);
    }


    @Operation(summary = "Get student by ID", description = "Returns student by ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }


    @Operation(summary = "Delete student", description = "Deletes student by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @Operation(summary = "Update student", description = "Updates student by ID")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable Long id) {
        StudentDto dto = studentService.updateStudent(studentDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}

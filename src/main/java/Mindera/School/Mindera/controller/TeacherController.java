package Mindera.School.Mindera.controller;

import Mindera.School.Mindera.dto.TeacherCreateDto;
import Mindera.School.Mindera.dto.TeacherDto;
import Mindera.School.Mindera.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {


    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Get all teachers", description = "Returns all teachers in database")
    @GetMapping("/all")
    public ResponseEntity<List<TeacherDto>> teacherDtoList() {
        return new ResponseEntity<>(teacherService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get teacher by ID", description = "Returns teacher by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getTeacher(id), HttpStatus.OK);
    }

    @Operation(summary = "Add new teacher", description = "Adds new teacher")
    @PostMapping
    public ResponseEntity<TeacherDto> addNewTeacher(@RequestBody TeacherCreateDto teacherDto) {
        return new ResponseEntity<>(teacherService.addTeacher(teacherDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete teacher", description = "Deletes teacher by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update teacher", description = "Updates teacher by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(id, teacherDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Add teacher to course", description = "Adds teacher to course")
    @PutMapping("/{id}/course/{courseid}")
    public ResponseEntity<TeacherDto> addTeacherToCourse(@PathVariable("id") Long id, @PathVariable("courseid") Long courseid) {
        return new ResponseEntity<>(teacherService.addTeacherToCourse(id, courseid), HttpStatus.OK);
    }

}

package Mindera.School.Mindera.controller;

import Mindera.School.Mindera.dto.TeacherCreateDto;
import Mindera.School.Mindera.dto.TeacherDto;
import Mindera.School.Mindera.service.TeacherService;
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

    @GetMapping("/all")
    public ResponseEntity<List<TeacherDto>> teacherDtoList() {
        return new ResponseEntity<>(teacherService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getTeacher(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TeacherDto> addNewTeacher(@RequestBody TeacherCreateDto teacherDto) {
        return new ResponseEntity<>(teacherService.addTeacher(teacherDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(id, teacherDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}/course/{courseid}")
    public ResponseEntity<TeacherDto> addTeacherToCourse(@PathVariable("id") Long id, @PathVariable("courseid") Long courseid) {
        return new ResponseEntity<>(teacherService.addTeacherToCourse(id, courseid), HttpStatus.OK);
    }

}

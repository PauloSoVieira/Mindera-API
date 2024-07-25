package Mindera.School.Mindera.controller;


import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
import Mindera.School.Mindera.repository.CourseRepository;
import Mindera.School.Mindera.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    private CourseService courseService;
    private CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService) {

        this.courseService = courseService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> courseDtoList() {
        return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> courseDto(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.findByIdDto(id), HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<CourseDto> courseDto(@RequestBody CourseCreationDto courseDto) {
        return new ResponseEntity<>(courseService.addNewCourse(courseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        CourseDto dto = courseService.updateCourse(courseDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}

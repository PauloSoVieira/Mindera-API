package Mindera.School.Mindera.dto;

import lombok.Data;

import java.util.List;

@Data

public class TeacherDto {
    private Long id;
    private String name;
    private List<CourseDto> courses;
}

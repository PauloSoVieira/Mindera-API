package Mindera.School.Mindera.dto;

import Mindera.School.Mindera.model.Course;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class StudentCreationDto {
    private Long id;
    @NotEmpty
    private String name;
    private List<Course> courseNames;

    @Email


    public StudentCreationDto(Long id, String name, List<Course> courseNames) {
        this.id = id;
        this.name = name;
        this.courseNames = courseNames;
    }
}

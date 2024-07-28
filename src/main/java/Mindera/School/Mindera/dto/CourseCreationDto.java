package Mindera.School.Mindera.dto;


import lombok.Data;

@Data

public class CourseCreationDto {
    private Long id;
    private String name;

    public CourseCreationDto(String name) {
        this.name = name;
    }

    public CourseCreationDto() {
    }
}

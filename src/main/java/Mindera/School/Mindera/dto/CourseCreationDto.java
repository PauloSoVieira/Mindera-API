package Mindera.School.Mindera.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CourseCreationDto {
    private Long id;
    @NotBlank
    @NotNull
    private String name;

    public CourseCreationDto(String name) {
        this.name = name;
    }

    public CourseCreationDto() {
    }
}

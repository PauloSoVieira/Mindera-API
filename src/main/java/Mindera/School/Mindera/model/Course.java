package Mindera.School.Mindera.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

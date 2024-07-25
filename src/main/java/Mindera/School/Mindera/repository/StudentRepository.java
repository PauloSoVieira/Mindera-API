package Mindera.School.Mindera.repository;

import Mindera.School.Mindera.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

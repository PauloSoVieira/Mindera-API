package Mindera.School.Mindera.mapper;


import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import Mindera.School.Mindera.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    //  @Mapping(source = "courses", target = "courses")
    StudentDto toDto(Student student);

    StudentCreationDto toDtoCreating(Student student);

    // @Mapping(source = "courses", target = "courses")
    Student toModel(StudentDto studentDto);

    Student toModelCreating(StudentCreationDto studentDto);

    List<StudentDto> studentsToStudentsDto(List<Student> students);

    List<Student> studentDtoToStudents(List<StudentDto> studentDto);


}

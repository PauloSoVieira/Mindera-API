package Mindera.School.Mindera.mapper;

import Mindera.School.Mindera.dto.TeacherCreateDto;
import Mindera.School.Mindera.dto.TeacherDto;
import Mindera.School.Mindera.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherMapper {


    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);


    TeacherDto toDto(Teacher teacher);

    TeacherCreateDto toDtoCreating(Teacher teacher);

    Teacher toModel(TeacherDto teacherDto);

    Teacher toModelCreate(TeacherCreateDto teacherCreateDto);

    List<TeacherDto> teacherToCoursesDto(List<Teacher> teachers);

    List<Teacher> teacherDtoToCourses(List<TeacherDto> teacherDtos);
}

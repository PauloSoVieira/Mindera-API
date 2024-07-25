package Mindera.School.Mindera.mapper;


import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
import Mindera.School.Mindera.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper


public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    
    CourseDto toDto(Course course);

    CourseCreationDto toDtoCreating(Course course);

    Course toModel(CourseDto courseDto);

    Course toModelCreate(CourseCreationDto courseDto);

    List<CourseDto> courseToCoursesDto(List<Course> courses);

    List<Course> courseDtoToCourses(List<CourseDto> courseDtos);

}

package Mindera.School.Mindera.handler;

import Mindera.School.Mindera.dto.ErrorDto;
import Mindera.School.Mindera.exception.CourseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CourseExceptionHandler {

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)

    @ExceptionHandler({CourseException.class})


    public ErrorDto handler(CourseException courseException) {
        return new ErrorDto(courseException.getMessage());
    }
}

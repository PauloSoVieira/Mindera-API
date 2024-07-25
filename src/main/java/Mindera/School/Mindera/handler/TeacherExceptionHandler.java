package Mindera.School.Mindera.handler;

import Mindera.School.Mindera.dto.ErrorDto;
import Mindera.School.Mindera.exception.TeacherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeacherExceptionHandler {


    @ResponseStatus(value = HttpStatus.NOT_FOUND)

    @ExceptionHandler(TeacherException.class)

    public ErrorDto handler(TeacherException e) {
        return new ErrorDto(e.getMessage());
    }
}

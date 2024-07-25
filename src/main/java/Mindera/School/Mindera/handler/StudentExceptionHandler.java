package Mindera.School.Mindera.handler;

import Mindera.School.Mindera.dto.ErrorDto;
import Mindera.School.Mindera.exception.StudentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class StudentExceptionHandler {


    @ResponseStatus(value = HttpStatus.NOT_FOUND)

    @ExceptionHandler(StudentException.class)

    public ErrorDto handler(StudentException e) {
        return new ErrorDto(e.getMessage());
    }
}

package Mindera.School.Mindera.exception;


import Mindera.School.Mindera.message.Message;
import lombok.Data;

@Data
public class CourseException extends RuntimeException {

    public CourseException(Message message) {
        super(message.getMessage());
    }
}

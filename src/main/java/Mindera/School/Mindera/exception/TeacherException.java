package Mindera.School.Mindera.exception;

import Mindera.School.Mindera.message.Message;

public class TeacherException extends RuntimeException {
    public TeacherException(Message message) {
        super(message.getMessage());
    }
}

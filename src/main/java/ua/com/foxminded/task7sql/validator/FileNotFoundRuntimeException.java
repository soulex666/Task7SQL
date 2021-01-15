package ua.com.foxminded.task7sql.validator;

public class FileNotFoundRuntimeException extends RuntimeException {
    public FileNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

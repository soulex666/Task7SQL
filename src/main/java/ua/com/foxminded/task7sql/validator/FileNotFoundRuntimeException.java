package ua.com.foxminded.task7sql.validator;

public class FileNotFoundRuntimeException extends RuntimeException {

    public FileNotFoundRuntimeException() {
    }

    public FileNotFoundRuntimeException(String message) {
        super(message);
    }

    public FileNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundRuntimeException(Throwable cause) {
        super(cause);
    }
}

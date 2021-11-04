package lingogo.commons.exceptions;

public class DataFileAsDirectoryException extends Exception {

    public DataFileAsDirectoryException(String message) {
        super(message);
    }

    public DataFileAsDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}

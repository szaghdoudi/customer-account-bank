package customeraccount.exceptions;

public class DataValidationException extends RuntimeException {
    public DataValidationException(String message) {
        super(message);
    }

    public static void throwException(String message) {
        throw new DataValidationException(message);
    }
}

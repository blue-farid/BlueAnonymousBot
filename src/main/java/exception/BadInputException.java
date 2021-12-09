package exception;

public class BadInputException extends IllegalArgumentException {
    public BadInputException(String message) {
        super(message);
    }
}

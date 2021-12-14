package exception;


/**
 * The Bad input exception.
 */
public class BadInputException extends IllegalArgumentException {
    /**
     * Instantiates a new Bad input exception.
     *
     * @param message the message
     */
    public BadInputException(String message) {
        super(message);
    }
}

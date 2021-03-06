package exception;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * The Bad input exception.
 * used when the client enter unknown inputs.
 * @author Farid Masjedi
 */
public class BadInputException extends IllegalArgumentException {
    private final SendMessage sendMessage;

    /**
     * Instantiates a new Bad input exception.
     *
     * @param message the message
     */
    public BadInputException(String message, String chatId) {
        super(message);
        sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }
}

package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;

public class BlockCommand extends Command {
    private final long contactId;

    public BlockCommand(String chatId, long contactId) {
        super(chatId);
        this.contactId = contactId;
    }

    @Override
    public void execute() {
        Client contact = ClientDao.getInstance().searchById(this.contactId);
        if (contact.isAdmin()) {
            this.sendMessage.setText("" + contact.getTelegramUser().getFirstName()
            + " tries to block you!");
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        } else {
            // who cares?
        }
    }
}

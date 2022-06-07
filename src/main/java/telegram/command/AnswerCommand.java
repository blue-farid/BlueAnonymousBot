package telegram.command;

import dao.ClientDao;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

public class AnswerCommand extends Command {
    private final String localMessage;
    private final long contactId;
    private final int currentMessageId;
    private final int contactMessageId;


    public AnswerCommand(Client client, long contactId, int currentMessageId, int contactMessageId) {
        super(client);
        this.contactId = contactId;
        this.currentMessageId = currentMessageId;
        this.contactMessageId = contactMessageId;
        this.localMessage= Property.MESSAGES_P.get("answer");
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        Client contact = ClientDao.getInstance().searchById(contactId);
        ClientService.getInstance().setContact(client, contact.getId());
        sendMessage.setText(localMessage);
        sendMessage.setReplyMarkup(CancelMenu.getInstance());
        sendMessage.setReplyToMessageId(currentMessageId);
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        ClientService.getInstance().setContactMessageId(client, contactMessageId);
        ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_TO_CONTACT);

    }
}

package telegram.command;

import dao.ClientDao;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class AnswerCommand extends Command {
    private static final String localMessage = "☝️ در حال پاسخ دادن به فرستنده این پیام هستی ... ؛ منتظریم بفرستی :)";
    private final Client client;
    private  long contactId;


    public AnswerCommand(String chatId, Client client, long contactId) {
        super(chatId);
        this.client = client;
        this.contactId = contactId;
    }

    @Override
    public void execute() {
        Client contact = ClientDao.getInstance().searchById(contactId);
        ClientService.getInstance().setContact(client, contact.getId());
        sendMessage.setText(localMessage);
        sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_WITH_DEEPLINK);

    }
}

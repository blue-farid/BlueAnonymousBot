package telegram.command;

import dao.ClientDao;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import telegram.BlueAnonymousBot;

public class AnswerCommand extends Command {
    private static final String localMessage = "☝️ در حال پاسخ دادن به فرستنده این پیام هستی ... ؛ منتظریم بفرستی :)";
    private final Client client;
    private final String deepLink;


    public AnswerCommand(String chatId, Client client, String deepLink) {
        super(chatId);
        this.client = client;
        this.deepLink = deepLink;
    }

    @Override
    public void execute() {

        Client contact = ClientDao.getInstance().searchByDeepLink(deepLink);
        client.setContact(contact);
        sendMessage.setText(localMessage);
        sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        client.setClientState(ClientState.SENDING_MESSAGE_WITH_DEEPLINK);

    }
}

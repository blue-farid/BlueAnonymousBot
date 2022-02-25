package telegram.command;

import dao.ClientDao;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import properties.Property;
import telegram.BlueAnonymousBot;

public class AnswerCommand extends Command {
    private final String localMessage ;
    private final Client client;
    private final String deepLink;


    public AnswerCommand(String chatId, Client client, String deepLink) {
        super(chatId);
        this.client = client;
        this.deepLink = deepLink;
        this.localMessage= Property.MESSAGES_P.get("answer");
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

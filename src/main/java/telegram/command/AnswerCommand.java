package telegram.command;

import dao.ClientDao;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import telegram.BlueAnonymousBot;

public class AnswerCommand extends Command {
    private static final String localMessage = "☝️ در حال پاسخ دادن به فرستنده این پیام هستی ... ؛ منتظریم بفرستی :)";
    private Client client;
    private String deepLink;

    public AnswerCommand(String chatId) {
        super(chatId);
    }

    public AnswerCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    public AnswerCommand(String chatId, Client client, String deepLink) {
        super(chatId);
        this.client = client;
        this.deepLink = deepLink;
    }

    @Override
    public void execute() {
//        System.out.println(client.getShortDeepLink());
//        System.out.println(deepLink);
        Client contact = ClientDao.getInstance().searchByDeepLink(deepLink);
        client.setContact(contact);
//        System.out.println("seeker "+client.getTelegramUser().getUserName()+"sender "+contact.getTelegramUser().getUserName());
        sendMessage.setText(localMessage);
        sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        client.setClientState(ClientState.SENDING_MESSAGE_WITH_DEEPLINK);

    }
}

package telegram.command;

import model.Client;
import model.ClientState;
import telegram.BlueAnonymousBot;

public class SendMessageWithDeepLinkCommand extends Command {

    private final Client client;
    private final String message;
    public SendMessageWithDeepLinkCommand(String chatId, Client client, String message) {
        super(chatId);
        this.client = client;
        this.message = message;
    }

    @Override
    public void execute() {
        sendMessage.setChatId(client.getContact().getChatId().toString());
        sendMessage.setText(message);
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        client.setClientState(ClientState.NORMAL);
    }
}

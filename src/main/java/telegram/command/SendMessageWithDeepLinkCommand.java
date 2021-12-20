package telegram.command;

import inlineMenu.InlineAMB;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        SendMessage contactSendMessage = new SendMessage();
        contactSendMessage.setChatId(client.getContact().getChatId().toString());
        contactSendMessage.setText("⚠️ پیام ناشناس جدید! ⚠️\n\n" + this.message);
        contactSendMessage.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);

        client.setClientState(ClientState.NORMAL);
    }
}

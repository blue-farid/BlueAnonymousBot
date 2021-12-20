package telegram.command;

import inlineMenu.InlineAMB;
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
        if (client.getContact().isAdmin()) {
            contactSendMessage.setText(contactSendMessage.getText() + "\n" +
                    "username: " + client.getTelegramUser().getUserName()
            + "\nfirstname: " + client.getTelegramUser().getFirstName()
            + "\nlastname: " + client.getTelegramUser().getLastName());
        }
        contactSendMessage.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        client.setClientState(ClientState.NORMAL);
    }
}

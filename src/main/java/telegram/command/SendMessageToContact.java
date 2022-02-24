package telegram.command;

import inlineMenu.InlineAMB;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.BlueAnonymousBot;

public class SendMessageToContact extends Command {

    private final Client client;
    private final Message message;

    public SendMessageToContact(String chatId, Client client, Message message) {
        super(chatId);
        this.client = client;
        this.message = message;
    }

    @Override
    public void execute() {
        String contactChatId = client.getContact().getChatId().toString();
        notifyNewMessage();
        if (message.hasText()){
            String text = message.getText();
            SendMessage contactSendMessage = new SendMessage();
            contactSendMessage.setChatId(contactChatId);
            contactSendMessage.setText(text);
            contactSendMessage.setEntities(message.getEntities());
            contactSendMessage.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
            BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
        }
        else if (message.hasSticker()){
            SendSticker contactSendSticker = new SendSticker();
            contactSendSticker.setChatId(contactChatId);
            InputFile sticker = new InputFile(message.getSticker().getFileId().toString());
            contactSendSticker.setSticker(sticker);
            contactSendSticker.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
            try {
                BlueAnonymousBot.getInstance().execute(contactSendSticker);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
//        else if (message.hasPhoto() || message.hasVideo()){
//            SendPhoto contactSendPhoto = new SendPhoto();
//            SendMediaGroup contactSendMedia = new SendMediaGroup();
//            message.getMed
//            contactSendPhoto.setChatId(contactChatId);
//            InputFile photo = new InputFile(message.getPhoto())
//            contactSendMedia.
//        }

        if (client.getContact().isAdmin()) {
            SendMessage adminSendMessage = new SendMessage();
            adminSendMessage.setChatId(contactChatId);
            adminSendMessage.setText("Sender:" + "\n" +
                    "username: " + client.getTelegramUser().getUserName()
                    + "\nfirstname: " + client.getTelegramUser().getFirstName()
                    + "\nlastname: " + client.getTelegramUser().getLastName());
            BlueAnonymousBot.getInstance().executeSendMessage(adminSendMessage);
        }

        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);

        client.setClientState(ClientState.NORMAL);
    }

    private void notifyNewMessage(){
        String contactChatId = client.getContact().getChatId().toString();
        SendMessage contactSendMessage = new SendMessage();
        contactSendMessage.setChatId(contactChatId);
        contactSendMessage.setText("\uD83D\uDCEC شما یک پیام ناشناس جدید دارید !");
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
    }
}

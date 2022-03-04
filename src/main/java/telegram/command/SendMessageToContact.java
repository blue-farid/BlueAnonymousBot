package telegram.command;

import inlineMenu.InlineAMB;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.*;
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
        if (message == null || client.getContact() == null){
            client.setClientState(ClientState.NORMAL);
            return;
        }
        String contactChatId = client.getContact().getChatId().toString();
        notifyNewMessage();
        if (message.hasText()){
            String text = message.getText();
            SendMessage contactSendMessage = new SendMessage();
            contactSendMessage.setChatId(contactChatId);
            contactSendMessage.setText(text);
            contactSendMessage.setEntities(message.getEntities());
            contactSendMessage.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendMessage.setReplyToMessageId(client.getContactMessageId());
            BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
        }
        else if (message.hasSticker()){
            InputFile sticker = new InputFile(message.getSticker().getFileId());
            SendSticker contactSendSticker = new SendSticker(contactChatId, sticker);
            contactSendSticker.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendSticker.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendSticker);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (message.hasVoice()){
            InputFile voice = new InputFile(message.getVoice().getFileId());
            SendVoice contactSendVoice = new SendVoice(contactChatId, voice);
            contactSendVoice.setCaption(message.getCaption());
            contactSendVoice.setCaptionEntities(message.getCaptionEntities());
            contactSendVoice.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendVoice.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendVoice);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (message.hasDocument()){
            InputFile document = new InputFile(message.getDocument().getFileId());
            SendDocument contactSendDocument = new SendDocument(contactChatId, document);
            contactSendDocument.setCaption(message.getCaption());
            contactSendDocument.setCaptionEntities(message.getCaptionEntities());
            contactSendDocument.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendDocument.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendDocument);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (message.hasPhoto()){
            InputFile photo = new InputFile(message.getPhoto().get(0).getFileId());
            SendPhoto contactSendPhoto = new SendPhoto(contactChatId, photo);
            contactSendPhoto.setCaption(message.getCaption());
            contactSendPhoto.setCaptionEntities(message.getCaptionEntities());
            contactSendPhoto.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendPhoto.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendPhoto);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (message.hasVideo()){
            InputFile video = new InputFile(message.getVideo().getFileId());
            SendVideo contactSendVideo = new SendVideo(contactChatId, video);
            contactSendVideo.setCaption(message.getCaption());
            contactSendVideo.setCaptionEntities(message.getCaptionEntities());
            contactSendVideo.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendVideo.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendVideo);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (message.hasAudio()){
            InputFile audio = new InputFile(message.getAudio().getFileId());
            SendAudio contactSendAudio = new SendAudio(contactChatId, audio);
            contactSendAudio.setCaption(message.getCaption());
            contactSendAudio.setCaptionEntities(message.getCaptionEntities());
            contactSendAudio.setReplyMarkup(new InlineAMB(client.getShortDeepLink(), message.getMessageId()));
            contactSendAudio.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendAudio);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

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
        client.setContactMessageId(null);
        client.setContact(null);
    }

    private void notifyNewMessage(){
        String contactChatId = client.getContact().getChatId().toString();
        SendMessage contactSendMessage = new SendMessage();
        contactSendMessage.setChatId(contactChatId);
        contactSendMessage.setText("\uD83D\uDCEC شما یک پیام ناشناس جدید دارید !");
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
    }
}

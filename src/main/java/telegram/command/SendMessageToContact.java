package telegram.command;

import console.ConsoleWriter;
import inlineMenu.InlineAMB;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.apache.log4j.MDC;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

@Monitor
public class SendMessageToContact extends Command {

    protected final Message message;

    public SendMessageToContact(Client client, Message message) {
        super(client);
        this.message = message;
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        if (message == null){
            ClientService.getInstance().setClientState(client, ClientState.NORMAL);
            return;
        }
        String contactChatId = ClientService.getInstance().getContact(client).getChatId().toString();
        try {
            MDC.put("others", ConsoleWriter.readyForLog("message: {" + message.getText().replace("\n", " ") + "}") + ConsoleWriter.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
        } catch (NullPointerException e) {
            MDC.put("others", ConsoleWriter.readyForLog("message: {not text!}") + ConsoleWriter.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
        }
        addBaseLog();
        notifyNewMessage();
        if (message.hasText()){
            String text = message.getText();
            SendMessage contactSendMessage = new SendMessage();
            contactSendMessage.setChatId(contactChatId);
            contactSendMessage.setText(text);
            contactSendMessage.setEntities(message.getEntities());
            contactSendMessage.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendMessage.setReplyToMessageId(client.getContactMessageId());
            int res = BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
            if(res != 0) {
                if (res == 400) {
                    // reply not found
                    contactSendMessage.setReplyToMessageId(null);
                    if (BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage) != 0) {
                        // failed again!
                        return;
                    }
                } else {
                    return;
                }
            }
        }
        else if (message.hasSticker()){
            InputFile sticker = new InputFile(message.getSticker().getFileId());
            SendSticker contactSendSticker = new SendSticker(contactChatId, sticker);
            contactSendSticker.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendSticker.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendSticker);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasVoice()){
            InputFile voice = new InputFile(message.getVoice().getFileId());
            SendVoice contactSendVoice = new SendVoice(contactChatId, voice);
            contactSendVoice.setCaption(message.getCaption());
            contactSendVoice.setCaptionEntities(message.getCaptionEntities());
            contactSendVoice.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVoice.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendVoice);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasDocument()){
            InputFile document = new InputFile(message.getDocument().getFileId());
            SendDocument contactSendDocument = new SendDocument(contactChatId, document);
            contactSendDocument.setCaption(message.getCaption());
            contactSendDocument.setCaptionEntities(message.getCaptionEntities());
            contactSendDocument.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendDocument.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendDocument);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasPhoto()){
            InputFile photo = new InputFile(message.getPhoto().get(0).getFileId());
            SendPhoto contactSendPhoto = new SendPhoto(contactChatId, photo);
            contactSendPhoto.setCaption(message.getCaption());
            contactSendPhoto.setCaptionEntities(message.getCaptionEntities());
            contactSendPhoto.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendPhoto.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendPhoto);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasVideo()){
            InputFile video = new InputFile(message.getVideo().getFileId());
            SendVideo contactSendVideo = new SendVideo(contactChatId, video);
            contactSendVideo.setCaption(message.getCaption());
            contactSendVideo.setCaptionEntities(message.getCaptionEntities());
            contactSendVideo.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideo.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendVideo);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasAudio()){
            InputFile audio = new InputFile(message.getAudio().getFileId());
            SendAudio contactSendAudio = new SendAudio(contactChatId, audio);
            contactSendAudio.setCaption(message.getCaption());
            contactSendAudio.setCaptionEntities(message.getCaptionEntities());
            contactSendAudio.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendAudio.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendAudio);
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        else if (message.hasVideoNote()){
            InputFile videoNote = new InputFile(message.getVideoNote().getFileId());
            SendVideoNote contactSendVideoNote = new SendVideoNote(contactChatId, videoNote);
            contactSendVideoNote.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideoNote.setReplyToMessageId(client.getContactMessageId());
            try {
                BlueAnonymousBot.getInstance().execute(contactSendVideoNote);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if (ClientService.getInstance().getContact(client).isAdmin()) {
            SendMessage adminSendMessage = new SendMessage();
            adminSendMessage.setChatId(contactChatId);
            adminSendMessage.setText("Sender:" + "\n" +
                    "username: " + client.getTelegramUser().getUserName()
                    + "\nfirstname: " + client.getTelegramUser().getFirstName()
                    + "\nlastname: " + client.getTelegramUser().getLastName());
            BlueAnonymousBot.getInstance().executeSendMessage(adminSendMessage);
        }

        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText(Property.MESSAGES_P.get("send_message_done"));
        sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        ClientService.getInstance().setContactMessageId(client, 0);
        ClientService.getInstance().setContact(client, 0);
        ClientService.getInstance().setClientState(client, ClientState.NORMAL);
    }

    private void notifyNewMessage(){
        String contactChatId = ClientService.getInstance().getContact(client).getChatId().toString();
        SendMessage contactSendMessage = new SendMessage();
        contactSendMessage.setChatId(contactChatId);
        contactSendMessage.setText(Property.MESSAGES_P.get("new_anonymous_message"));
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
    }
}

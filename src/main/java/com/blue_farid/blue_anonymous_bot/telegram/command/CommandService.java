package com.blue_farid.blue_anonymous_bot.telegram.command;

import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.inlineMenu.InlineAMB;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.ClientState;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.BlueAnonymousBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final ClientService clientService;
    private final Environment env;

    private final BlueAnonymousBot bot;

    @SneakyThrows
    @Response(value = "/start")
    public void start(RequestDto requestDto) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(Objects.requireNonNull(env.getProperty("start")));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        sendMessage.setChatId(requestDto.client().getId());
        bot.execute(sendMessage);
    }

    @SneakyThrows
    @Response(value = "answer")
    public void answer(RequestDto requestDto) {
        clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_CONTACT);
        bot.execute(new SendMessage(requestDto.client().getTelegramUser().getId().toString(), Objects.requireNonNull(env.getProperty("answer"))));
    }

    @SneakyThrows
    @Response(acceptedState = ClientState.SENDING_MESSAGE_TO_CONTACT)
    public void sendMessage(RequestDto requestDto) {
        Client client = requestDto.client();
        Message message = requestDto.value();
        String contactChatId = String.valueOf(clientService.getContact(client).getContactId());
        if (message == null) {
            clientService.setClientState(client, ClientState.NORMAL);
            return;
        }
        if (message.hasText()) {
            String text = message.getText();
            SendMessage contactSendMessage = new SendMessage();
            contactSendMessage.setChatId(contactChatId);
            contactSendMessage.setText(text);
            contactSendMessage.setEntities(message.getEntities());
            contactSendMessage.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendMessage.setReplyToMessageId(client.getContactMessageId());
            bot.executeSendMessage(contactSendMessage);
        } else if (message.hasSticker()) {
            InputFile sticker = new InputFile(message.getSticker().getFileId());
            SendSticker contactSendSticker = new SendSticker(contactChatId, sticker);
            contactSendSticker.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendSticker.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendSticker);
        } else if (message.hasVoice()) {
            InputFile voice = new InputFile(message.getVoice().getFileId());
            SendVoice contactSendVoice = new SendVoice(contactChatId, voice);
            contactSendVoice.setCaption(message.getCaption());
            contactSendVoice.setCaptionEntities(message.getCaptionEntities());
            contactSendVoice.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVoice.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVoice);
        } else if (message.hasDocument()) {
            InputFile document = new InputFile(message.getDocument().getFileId());
            SendDocument contactSendDocument = new SendDocument(contactChatId, document);
            contactSendDocument.setCaption(message.getCaption());
            contactSendDocument.setCaptionEntities(message.getCaptionEntities());
            contactSendDocument.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendDocument.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendDocument);
        } else if (message.hasPhoto()) {
            InputFile photo = new InputFile(message.getPhoto().get(0).getFileId());
            SendPhoto contactSendPhoto = new SendPhoto(contactChatId, photo);
            contactSendPhoto.setCaption(message.getCaption());
            contactSendPhoto.setCaptionEntities(message.getCaptionEntities());
            contactSendPhoto.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendPhoto.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendPhoto);
        } else if (message.hasVideo()) {
            InputFile video = new InputFile(message.getVideo().getFileId());
            SendVideo contactSendVideo = new SendVideo(contactChatId, video);
            contactSendVideo.setCaption(message.getCaption());
            contactSendVideo.setCaptionEntities(message.getCaptionEntities());
            contactSendVideo.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideo.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVideo);
        } else if (message.hasAudio()) {
            InputFile audio = new InputFile(message.getAudio().getFileId());
            SendAudio contactSendAudio = new SendAudio(contactChatId, audio);
            contactSendAudio.setCaption(message.getCaption());
            contactSendAudio.setCaptionEntities(message.getCaptionEntities());
            contactSendAudio.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendAudio.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendAudio);
        } else if (message.hasVideoNote()) {
            InputFile videoNote = new InputFile(message.getVideoNote().getFileId());
            SendVideoNote contactSendVideoNote = new SendVideoNote(contactChatId, videoNote);
            contactSendVideoNote.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideoNote.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVideoNote);
        } else {
            throw new IllegalStateException();
        }

        if (clientService.getContact(client).isAdmin()) {
            SendMessage adminSendMessage = new SendMessage();
            adminSendMessage.setChatId(contactChatId);
            adminSendMessage.setText("Sender:" + "\n" +
                    "username: " + client.getUsername()
                    + "\nfirstname: " + client.getFirstname()
                    + "\nlastname: " + client.getLastname());
            bot.executeSendMessage(adminSendMessage);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(client.getId()));
        sendMessage.setText(Objects.requireNonNull(env.getProperty("send_message_done")));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        bot.executeSendMessage(sendMessage);
        clientService.setContactMessageId(client, 0);
        clientService.setContact(client, 0);
        clientService.setClientState(client, ClientState.NORMAL);
    }
}

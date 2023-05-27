package com.blue_farid.blue_anonymous_bot.telegram.command;

import com.blue_farid.blue_anonymous_bot.annotation.AdminCommand;
import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.inlineMenu.InlineAMB;
import com.blue_farid.blue_anonymous_bot.inlineMenu.InlineHelpKeyBoard;
import com.blue_farid.blue_anonymous_bot.mapper.GenderMapper;
import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.ClientState;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.service.AnonymousConnectionRequestService;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import com.blue_farid.blue_anonymous_bot.telegram.BlueAnonymousBot;
import com.blue_farid.blue_anonymous_bot.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandService {
    private final ClientService clientService;

    private final MessageSource source;

    private final LocaleUtils localeUtils;

    private final BlueAnonymousBot bot;

    private final RandomUtils randomUtils;

    private final AnonymousConnectionRequestService anonymousConnectionRequestService;

    private final GenderMapper genderMapper;

    private final InlineHelpKeyBoard helpKeyBoard;

    private final FileUtils fileUtils;

    private final MetricUtil metricUtil;

    @Response(value = CommandConstant.CANCEL, acceptedStates = {ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT, ClientState.ADMIN_SENDING_CONTACT_ID,
            ClientState.SENDING_CONTACT_INFO, ClientState.CHOOSING_CONTACT_GENDER, ClientState.NORMAL, ClientState.WAITING_FOR_CONTACT})
    @SneakyThrows
    public void cancel(RequestDto requestDto) {
        if (requestDto.value().getText().equals(CommandConstant.CANCEL)) {
            log.info(requestDto.client().getClientInfo());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(requestDto.client().getId());
            sendMessage.setText(Objects.requireNonNull(source.getMessage("cancel", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getMainMenu());
            if (requestDto.client().getClientState().equals(ClientState.WAITING_FOR_CONTACT)) {
                anonymousConnectionRequestService.deleteByRequestFromId(requestDto.client().getId());
            }
            clientService.setClientState(requestDto.client(), ClientState.NORMAL);
            bot.execute(sendMessage);
        }
    }

    @SneakyThrows
    @Response(value = CommandConstant.START)
    public void start(RequestDto requestDto) {
        String[] commands = requestDto.value().getText().split(" ");
        SendMessage sendMessage = new SendMessage();
        if (commands.length == 2) {
            String link = getAnonymousLinkPrefix() + commands[1];
            Client contact = clientService.getClientByDeepLink(link);
            if (contact == null)
                throw new IllegalStateException();

            MDC.put("others", CommonUtils.readyForLog("trying to message : " + contact.getId()));
            sendMessage.setChatId(requestDto.client().getId());
            if (requestDto.client().getId() == contact.getId()) {
                sendMessage.setText(Objects.requireNonNull(source.getMessage("self_anonymous", null, localeUtils.getLocale())));
                bot.execute(sendMessage);
                return;
            }
            sendMessage.setText(Objects.requireNonNull(source.getMessage("start_2", null, localeUtils.getLocale())).replace("?name",
                    contact.getFirstname()));
            sendMessage.setReplyMarkup(bot.getCancelMenu());
            bot.execute(sendMessage);
            clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT);
            clientService.setContact(requestDto.client(), contact.getId());
        } else {
            sendMessage.setText(Objects.requireNonNull(source.getMessage("start", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getMainMenu());
            sendMessage.setChatId(requestDto.client().getId());
            bot.execute(sendMessage);
        }
        log.info(requestDto.client().getClientInfo());
    }

    @SneakyThrows
    @Response(value = CommandConstant.ANSWER)
    public void answer(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT);
        String[] texts = requestDto.value().getText().split(" ");
        clientService.setContact(requestDto.client(), Long.parseLong(texts[0]));
        clientService.setContactMessageId(requestDto.client(), Integer.parseInt(texts[1]));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(Objects.requireNonNull(source.getMessage("answer", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getCancelMenu());
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setReplyToMessageId(requestDto.value().getMessageId());
        bot.execute(sendMessage);
    }

    @SneakyThrows
    @Response(value = CommandConstant.BLOCK)
    public void block(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        String[] texts = requestDto.value().getText().split(" ");
        Client contact = clientService.getClientById(Long.parseLong(texts[0]));
        if (contact.isAdmin()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(requestDto.client().getId() + " tries to block you!");
            sendMessage.setChatId(contact.getId());
            bot.execute(sendMessage);
        }
    }

    @SneakyThrows
    @Response(value = CommandConstant.ANONYMOUS_TO_GROUP)
    public void anonymousToGroup(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @SneakyThrows
    @Response(value = CommandConstant.ADMIN_CONNECT)
    @AdminCommand
    public void adminConnect(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        clientService.setClientState(requestDto.client(), ClientState.ADMIN_SENDING_CONTACT_INFO);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("OK!, now send the contact ID!");
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setReplyMarkup(bot.getCancelMenu());
        bot.execute(sendMessage);
    }

    @Response(acceptedStates = ClientState.ADMIN_SENDING_CONTACT_INFO)
    @SneakyThrows
    @AdminCommand
    public void adminFindContact(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        requestDto.client().setContactId(clientService.getClientById(Long.parseLong(requestDto.value().getText())).getId());
        sendMessage.setReplyMarkup(bot.getCancelMenu());
        clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT);
        sendMessage.setText("OK! Send your message!");
        bot.execute(sendMessage);
    }

    @SneakyThrows
    @Response(value = CommandConstant.SCORE)
    public void score(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @SneakyThrows
    @Response(value = CommandConstant.HELP)
    public void help(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(Objects.requireNonNull(source.getMessage("help", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        sendMessage.setReplyMarkup(helpKeyBoard);
        sendMessage.setChatId(requestDto.client().getId());
        bot.execute(sendMessage);
    }

    @Response(value = CommandConstant.SEND_ANONYMOUS_MESSAGE_GROUP_HELP)
    public void helpAnonymousToGroup(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.WHAT_FOR_HELP)
    public void helpWhatFor(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.CONNECT_RANDOM_ANONYMOUS_HELP)
    public void helpRandomAnonymous(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.FREE_VIP_HELP)
    public void helpFreeVIP(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.SPECIFIC_CONNECTION_HELP)
    public void helpSpecificConnection(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.RECEIVE_ANONYMOUS_MESSAGE_HELP)
    public void helpReceiveAnonymousMessage(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }

    @Response(value = CommandConstant.BACK_HELP_MAIN_MENU)
    public void backHelpMainMenu(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
    }


    @Response(acceptedStates = ClientState.SENDING_MESSAGE_TO_CONTACT, value = CommandConstant.CANCEL_CHAT)
    @SneakyThrows
    public void stopAnonymousChat(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("stop_chat_confirm", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getChatStopConfirmMenu());
        clientService.setClientState(requestDto.client(), ClientState.CONFIRM_STOP_CHAT);
        bot.execute(sendMessage);
    }

    @Response(acceptedStates = ClientState.CONFIRM_STOP_CHAT, value = CommandConstant.CANCEL_CHAT_CONFIRM_YES)
    @SneakyThrows
    public void stopAnonymousChatConfirmYes(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        Client contact = clientService.getClientById(requestDto.client().getContactId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("start", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        clientService.setClientState(requestDto.client(), ClientState.NORMAL);
        clientService.setClientState(contact, ClientState.NORMAL);
        bot.execute(sendMessage);
        sendMessage.setChatId(contact.getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("close.chat.contact", null, localeUtils.getLocale())));
        bot.execute(sendMessage);
    }

    @Response(acceptedStates = ClientState.CONFIRM_STOP_CHAT, value = CommandConstant.CANCEL_CHAT_CONFIRM_NO)
    @SneakyThrows
    public void stopAnonymousChatConfirmNo(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("stop_chat_not_confirm", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_CONTACT);
        bot.execute(sendMessage);
    }

    @SneakyThrows
    @Response(acceptedStates = ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT)
    public void sendMessageToSpecific(RequestDto requestDto) {
        sendMessage(requestDto, true);
        notifyNewMessageToContact(requestDto.client());
        Client client = requestDto.client();
        SendMessage sendMessage = new SendMessage();
        if (clientService.getContact(client).isAdmin()) {
            SendMessage adminSendMessage = new SendMessage();
            adminSendMessage.setChatId(client.getContactId());
            adminSendMessage.setText("Sender:" + "\n" + requestDto.client().getClientInfo());
            bot.execute(adminSendMessage);
        }
        sendMessage.setChatId(String.valueOf(client.getId()));
        sendMessage.setText(Objects.requireNonNull(source.getMessage("send_message_done", null, localeUtils.getLocale())));
        bot.execute(sendMessage);
        clientService.setContactMessageId(client, 0);
        clientService.setContact(client, 0);
        clientService.setClientState(requestDto.client(), ClientState.NORMAL);
    }

    @Response(acceptedStates = ClientState.SENDING_MESSAGE_TO_CONTACT)
    @SneakyThrows
    public void sendMessageToAnonymous(RequestDto requestDto) {
        sendMessage(requestDto, false);
        Client client = requestDto.client();
        clientService.setContactMessageId(client, 0);
    }

    @SneakyThrows
    private void notifyNewMessageToContact(Client client) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(client.getContactId()));
        sendMessage.setText(Objects.requireNonNull(source.getMessage("new_anonymous_message", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        bot.execute(sendMessage);
    }

    @SneakyThrows
    private void sendMessage(RequestDto requestDto, boolean isSpecific) {
        Client client = requestDto.client();
        Message message = requestDto.value();
        String contactChatId = String.valueOf(clientService.getContact(client).getId());
        String monitor = "";
        if (message == null) {
            clientService.setClientState(client, ClientState.NORMAL);
            return;
        }

        if (message.hasText()) {
            MDC.put("others", CommonUtils.readyForLog("message: {" + message.getText().replace("\n", " ") + "}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            String text = message.getText();
            monitor = text;
            SendMessage contactSendMessage = new SendMessage();
            contactSendMessage.setChatId(contactChatId);
            contactSendMessage.setText(text);
            contactSendMessage.setEntities(message.getEntities());
            if (isSpecific)
                contactSendMessage.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendMessage.setReplyToMessageId(client.getContactMessageId());
            try {
                bot.execute(contactSendMessage);
            } catch (TelegramApiRequestException e) {
                if (e.getErrorCode() == 400) {
                    // reply not found!
                    contactSendMessage.setReplyToMessageId(null);
                    bot.execute(contactSendMessage);
                }
            }
        } else if (message.hasSticker()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Sticker}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Sticker";
            InputFile sticker = new InputFile(message.getSticker().getFileId());
            SendSticker contactSendSticker = new SendSticker(contactChatId, sticker);
            if (isSpecific)
                contactSendSticker.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendSticker.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendSticker);
        } else if (message.hasVoice()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Voice}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Voice";
            InputFile voice = new InputFile(message.getVoice().getFileId());
            SendVoice contactSendVoice = new SendVoice(contactChatId, voice);
            contactSendVoice.setCaption(message.getCaption());
            contactSendVoice.setCaptionEntities(message.getCaptionEntities());
            if (isSpecific)
                contactSendVoice.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVoice.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVoice);
        } else if (message.hasDocument()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Document}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Document";
            InputFile document = new InputFile(message.getDocument().getFileId());
            SendDocument contactSendDocument = new SendDocument(contactChatId, document);
            contactSendDocument.setCaption(message.getCaption());
            contactSendDocument.setCaptionEntities(message.getCaptionEntities());
            if (isSpecific)
                contactSendDocument.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendDocument.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendDocument);
        } else if (message.hasPhoto()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Photo}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Photo";
            InputFile photo = new InputFile(message.getPhoto().get(0).getFileId());
            SendPhoto contactSendPhoto = new SendPhoto(contactChatId, photo);
            contactSendPhoto.setCaption(message.getCaption());
            contactSendPhoto.setCaptionEntities(message.getCaptionEntities());
            if (isSpecific)
                contactSendPhoto.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendPhoto.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendPhoto);
        } else if (message.hasVideo()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Video}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Video";
            InputFile video = new InputFile(message.getVideo().getFileId());
            SendVideo contactSendVideo = new SendVideo(contactChatId, video);
            contactSendVideo.setCaption(message.getCaption());
            contactSendVideo.setCaptionEntities(message.getCaptionEntities());
            if (isSpecific)
                contactSendVideo.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideo.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVideo);
        } else if (message.hasAudio()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Audio}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Audio";
            InputFile audio = new InputFile(message.getAudio().getFileId());
            SendAudio contactSendAudio = new SendAudio(contactChatId, audio);
            contactSendAudio.setCaption(message.getCaption());
            contactSendAudio.setCaptionEntities(message.getCaptionEntities());
            if (isSpecific)
                contactSendAudio.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendAudio.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendAudio);
        } else if (message.hasVideoNote()) {
            MDC.put("others", CommonUtils.readyForLog("message: {Video Message}") + CommonUtils.readyForLog(
                    "contactId: {" + contactChatId + "}"
            ));
            monitor = "Video Message";
            InputFile videoNote = new InputFile(message.getVideoNote().getFileId());
            SendVideoNote contactSendVideoNote = new SendVideoNote(contactChatId, videoNote);
            if (isSpecific)
                contactSendVideoNote.setReplyMarkup(new InlineAMB(client.getId(), message.getMessageId()));
            contactSendVideoNote.setReplyToMessageId(client.getContactMessageId());
            bot.execute(contactSendVideoNote);
        } else {
            throw new IllegalStateException();
        }
        log.info(requestDto.client().getClientInfo());
        fileUtils.monitorSendMessageToContact("SendMessage",
                monitor, client);

        metricUtil.incrementTotalSendMessages();
    }

    @Response(value = CommandConstant.SPECIFIC_CONNECTION)
    @SneakyThrows
    public void specificConnection(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        clientService.setClientState(requestDto.client(), ClientState.SENDING_CONTACT_INFO);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("specific_connection", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getCancelMenu());
        bot.execute(sendMessage);
    }

    @Response(acceptedStates = ClientState.SENDING_CONTACT_INFO)
    @SneakyThrows
    public void findContact(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        Client contact;
        contact = findWithForwarded(requestDto.value());
        if (contact == null && !isUsername(requestDto.value().getText())) {
            sendMessage.setText(Objects.requireNonNull(source.getMessage("send_forwarded_message", null, localeUtils.getLocale())));
            bot.execute(sendMessage);
        } else {
            if (contact == null)
                contact = findWithUsername(requestDto.value());
        }
        if (contact != null) {
            if (contact.getId() == requestDto.client().getId()) {
                sendMessage.setText(Objects.requireNonNull(source.getMessage("self_anonymous", null, localeUtils.getLocale())));
                sendMessage.setReplyMarkup(bot.getMainMenu());
                bot.execute(sendMessage);
                clientService.setClientState(requestDto.client(), ClientState.NORMAL);
            } else {
                sendMessage.setText(Objects.requireNonNull(source.getMessage("find_contact_1", null, localeUtils.getLocale())).replace("?name",
                        contact.getFirstname()));
                bot.execute(sendMessage);
                clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_SPECIFIC_CONTACT);
                clientService.setContact(requestDto.client(), contact.getId());
            }

        } else {
            sendMessage.setText(Objects.requireNonNull(source.getMessage("find_contact_2", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getMainMenu());
            bot.execute(sendMessage);
            clientService.setClientState(requestDto.client(), ClientState.NORMAL);
        }
    }

    @Response(acceptedStates = ClientState.CHOOSING_CONTACT_GENDER)
    @SneakyThrows
    public void chooseContactGender(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        Gender selectedGender = genderMapper.persianGenderValueToGender(requestDto.value().getText());
        AnonymousConnectionRequest anonymousConnectionRequest;
        if (selectedGender.equals(Gender.BI))
            anonymousConnectionRequest = anonymousConnectionRequestService.connect(requestDto.client().getId());
        else
            anonymousConnectionRequest = anonymousConnectionRequestService.connect(requestDto.client().getId(), selectedGender);

        if (Objects.isNull(anonymousConnectionRequest)) {
            anonymousConnectionRequestService.submitRequest(requestDto.client(), selectedGender);
            sendMessage.setText(Objects.requireNonNull(source.getMessage("connection_pending", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getCancelMenu());
            clientService.setClientState(requestDto.client(), ClientState.WAITING_FOR_CONTACT);
            bot.execute(sendMessage);
        } else {
            clientService.setContact(requestDto.client(), anonymousConnectionRequest.getRequestFrom().getId());
            Client contact = clientService.getContact(requestDto.client());
            anonymousConnectionRequestService.deleteByRequestFromId(requestDto.client().getId());
            anonymousConnectionRequestService.deleteByRequestFromId(contact.getId());
            clientService.setContact(contact, requestDto.client().getId());
            clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_CONTACT);
            clientService.setClientState(contact, ClientState.SENDING_MESSAGE_TO_CONTACT);
            sendMessage.setText(Objects.requireNonNull(source.getMessage("connected.anonymous", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getAnonymousChatMenu());
            bot.execute(sendMessage);
            sendMessage.setChatId(contact.getId());
            bot.execute(sendMessage);
        }
    }

    @Response(value = CommandConstant.ANONYMOUS_CONNECTION)
    @SneakyThrows
    public void anonymousConnection(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());

        // gender not specified!

        if (Objects.isNull(requestDto.client().getGender())) {
            sendMessage.setText(Objects.requireNonNull(source.getMessage("gender.not.specified", null, localeUtils.getLocale())));
            bot.execute(sendMessage);
        } else {
            clientService.setClientState(requestDto.client(), ClientState.CHOOSING_CONTACT_GENDER);
            sendMessage.setText(Objects.requireNonNull(source.getMessage("anonymous_connection", null, localeUtils.getLocale())));
            sendMessage.setReplyMarkup(bot.getChooseContactGenderMenu());
            bot.execute(sendMessage);
        }
    }

    @Response(value = CommandConstant.ANONYMOUS_LINK)
    @SneakyThrows
    public void anonymousLink(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        SendMessage sendMessage = new SendMessage();
        if (!requestDto.client().hasDeepLink()) {
            clientService.setDeepLink(requestDto.client(), generateAnonymousLink());
        }
        sendMessage.setText(Objects.requireNonNull(source.getMessage("anonymous_link", null, localeUtils.getLocale())).replace("?name",
                        requestDto.client().getFirstname())
                .concat("\n" + requestDto.client().getDeepLink()));
        sendMessage.setChatId(requestDto.client().getId());
        bot.execute(sendMessage);
    }

    @SneakyThrows
    public void badInput(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        clientService.setClientState(requestDto.client(), ClientState.NORMAL);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(Objects.requireNonNull(source.getMessage("bad_input", null, localeUtils.getLocale())));
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setReplyMarkup(bot.getMainMenu());
        bot.execute(sendMessage);
    }

    @Response(value = CommandConstant.SET_GENDER)
    @SneakyThrows
    public void gender(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        clientService.setClientState(requestDto.client(), ClientState.SETTING_GENDER);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("gender.select", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getGenderMenu());
        bot.execute(sendMessage);
    }

    @Response(acceptedStates = ClientState.SETTING_GENDER)
    @SneakyThrows
    public void setGender(RequestDto requestDto) {
        log.info(requestDto.client().getClientInfo());
        Gender gender;
        if (requestDto.value().getText().equals(source.getMessage("gender.male", null, localeUtils.getLocale()))) {
            gender = Gender.MALE;
        } else if (requestDto.value().getText().equals(source.getMessage("gender.female", null, localeUtils.getLocale()))) {
            gender = Gender.FEMALE;
        } else {
            log.error("Unexpected Value for gender : " + requestDto.value().getText());
            MDC.put("method", "badInput");
            badInput(requestDto);
            return;
        }
        clientService.setGender(requestDto.client(), gender);
        clientService.setClientState(requestDto.client(), ClientState.NORMAL);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestDto.client().getId());
        sendMessage.setText(Objects.requireNonNull(source.getMessage("start", null, localeUtils.getLocale())));
        sendMessage.setReplyMarkup(bot.getMainMenu());
        bot.execute(sendMessage);
    }


    private Client findWithForwarded(Message message) {
        User forwardedFrom = message.getForwardFrom();
        if (forwardedFrom != null) {
            return clientService.getClientById(forwardedFrom.getId());
        }
        return null;
    }

    private boolean isUsername(String username) {
        return username.charAt(0) == '@';
    }

    private Client findWithUsername(Message message) {
        String text = message.getText();
        String username = text.replaceFirst("@", "");
        if (!username.contains(" ")) {
            return clientService.getClientByUsername(username);
        }
        return null;
    }

    private String generateAnonymousLink() {
        while (true) {
            String anonymousLink = getAnonymousLinkPrefix() + "sc";
            anonymousLink += "-";
            anonymousLink += randomUtils.generateRandomNumber(5);
            anonymousLink += "-";
            anonymousLink += randomUtils.generateRandomString(8);
            if (clientService.getClientByDeepLink(anonymousLink) == null) {
                return anonymousLink;
            }
        }
    }

    private String getAnonymousLinkPrefix() {
        return "https://t.me/" + bot.getBotUsername() + "?start=";
    }
}

package com.blue_farid.blue_anonymous_bot.telegram.command;

import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.model.ClientState;
import com.blue_farid.blue_anonymous_bot.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
public class CommandService {
    private final ClientService clientService;
    private final Environment env;

    @Response(value = "/start")
    public SendMessage start(RequestDto requestDto) {
        return new SendMessage(requestDto.client().getTelegramUser().getId().toString(), Objects.requireNonNull(env.getProperty("start")));
    }

    @Response(value = "answer")
    public SendMessage answer(RequestDto requestDto) {
        clientService.setClientState(requestDto.client(), ClientState.SENDING_MESSAGE_TO_CONTACT);
        return new SendMessage(requestDto.client().getTelegramUser().getId().toString(), Objects.requireNonNull(env.getProperty("answer")));
    }

    @Response(acceptedState = ClientState.SENDING_MESSAGE_TO_CONTACT)
    public SendMessage sendText(RequestDto requestDto) {
        return new SendMessage(requestDto.client().getTelegramUser().getId().toString(), requestDto.value().getText());
    }

    @Response(acceptedState = ClientState.SENDING_MESSAGE_TO_CONTACT)
    public SendAudio sendAudio(RequestDto requestDto) {
        return new SendAudio(requestDto.client().getTelegramUser().getId().toString(), new InputFile(requestDto.value().getAudio().getFileId()));
    }

    @Response(acceptedState = ClientState.SENDING_MESSAGE_TO_CONTACT)
    public SendSticker sendSticker(RequestDto requestDto) {
        return new SendSticker(requestDto.client().getTelegramUser().getId().toString(), new InputFile(requestDto.value().getSticker().getFileId()));
    }
}

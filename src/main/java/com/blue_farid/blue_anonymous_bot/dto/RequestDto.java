package com.blue_farid.blue_anonymous_bot.dto;

import com.blue_farid.blue_anonymous_bot.model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;

public record RequestDto(Client client, Message value) {
}

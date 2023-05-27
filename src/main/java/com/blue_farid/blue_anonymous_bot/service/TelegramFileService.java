package com.blue_farid.blue_anonymous_bot.service;

import com.blue_farid.blue_anonymous_bot.model.TelegramFile;
import com.blue_farid.blue_anonymous_bot.repository.TelegramFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TelegramFileService {
    private final TelegramFileRepository repository;


    public TelegramFile saveTelegramFile(TelegramFile telegramFile) {
        return repository.save(telegramFile);
    }

}

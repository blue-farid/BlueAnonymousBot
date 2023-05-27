package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.TelegramFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramFileRepository extends JpaRepository<TelegramFile, Long> {
}

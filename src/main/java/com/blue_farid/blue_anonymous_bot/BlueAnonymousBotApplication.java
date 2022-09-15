package com.blue_farid.blue_anonymous_bot;

import com.blue_farid.blue_anonymous_bot.utils.BlueStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ! The BlueAnonymousBot Project !
 * <p>
 * This is a Fake-Copy of a persian telegram ChatBot called 'برنامه ناشناس'
 * The original idea for the robot came about
 * when we wanted to know who was sending us anonymous messages
 * using 'برنامه ناشناس' bot.
 * SO WE WROTE OUR OWN COPY!
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 * @version 8.0.0
 * <p>
 * Telegram_id: @blue_farid
 * github: <a href="https://github.com/blue-farid/BlueAnonymousBot">...</a>
 */
@SpringBootApplication
@Slf4j
public class BlueAnonymousBotApplication {
    /**
     * the entry point of the Application.
     *
     * @param args the initial arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(BlueAnonymousBotApplication.class, args);
        log.info("\n" + BlueStringUtils.asciiBlueAnonymousBot() + "\n" + BlueStringUtils.asciiLogo() + "\n" + BlueStringUtils.initMessage("8.0.0"));
    }
}

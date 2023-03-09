package com.blue_farid.blue_anonymous_bot;

import com.blue_farid.blue_anonymous_bot.utils.BlueStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

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
 * @author Alireza Jabbari
 * @version 8.1.0
 * <p>
 * Telegram_id: @blue_farid
 * github: <a href="https://github.com/blue-farid/BlueAnonymousBot">...</a>
 */
@SpringBootApplication
@Slf4j
@PropertySource("classpath:bot_config.properties")
public class BlueAnonymousBotApplication {
    /**
     * the entry point of the Application.
     *
     * @param args the initial arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(BlueAnonymousBotApplication.class, args);
        System.out.println("\n" + BlueStringUtils.asciiBlueAnonymousBot() + "\n" + BlueStringUtils.asciiLogo() + "\n" + BlueStringUtils.initMessage("8.1.0"));
    }
}

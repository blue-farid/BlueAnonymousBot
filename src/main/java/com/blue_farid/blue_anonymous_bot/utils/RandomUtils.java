package com.blue_farid.blue_anonymous_bot.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtils {
    private final Random random = new Random(System.currentTimeMillis());
    public String generateRandomString(int len) {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char randChar;
            if (random.nextBoolean()) {
                randChar = (char) (random.nextInt(26) + 'a');
            } else {
                randChar = (char) (random.nextInt(26) + 'A');
            }
            string.append(randChar);
        }
        return string.toString();
    }

    public String generateRandomNumber(int len) {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randNum = random.nextInt(10);
            string.append(randNum);
        }
        return string.toString();
    }
}

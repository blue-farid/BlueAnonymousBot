package utils;

import java.util.Random;

public class RandomUtils {
    private static RandomUtils instance;
    private final Random random;

    private RandomUtils() {
        random = new Random(System.currentTimeMillis());
    }

    public static RandomUtils getInstance() {
        if (instance == null)
            instance = new RandomUtils();
        return instance;
    }

    public String generateRandomNumber(int len) {
        String string = "";

        for (int i = 0; i < len; i++) {
            int randNum = random.nextInt();
            string += randNum;
        }
        return string;
    }

    public String generateRandomString(int len) {
        String string = "";

        for (int i = 0; i < len; i++) {
            char randChar;
            if (random.nextBoolean()) {
                randChar = (char) (random.nextInt(26) + 'a');
            } else {
                randChar = (char) (random.nextInt(26) + 'A');
            }
            string += randChar + "";
        }
        return string;
    }

}

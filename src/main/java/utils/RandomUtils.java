package utils;

import java.util.Random;

/**
 * The RandomUtils Singleton class.
 * this class is used for all the random operations.
 *
 * @author Farid Masjedi
 */
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

    /**
     * generates a string of random digits.
     * @param len the length of the string.
     * @return the string.
     */
    public String generateRandomNumber(int len) {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randNum = random.nextInt(10);
            string.append(randNum);
        }
        return string.toString();
    }

    /**
     * generates a string of ascii letters.
     * @param len the length of the string.
     * @return the string.
     */
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

}

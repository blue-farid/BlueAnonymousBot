package log;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Console class
 * this class used to print some information in console.
 */
public class Console {
    public static void printNewRequestInfo(Update update) {
        System.out.println("- new Request:" +
                "\n" + "\t" + "from: " +
                update.getMessage().getFrom().getUserName());
    }
}

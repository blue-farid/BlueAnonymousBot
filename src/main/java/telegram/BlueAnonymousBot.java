package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.command.AnonymousLinkCommand;
import telegram.command.Command;

/**
 * the BlueAnonymousBot class
 */
public class BlueAnonymousBot extends TelegramLongPollingBot {
    private final UpdateHandler updateHandler = new UpdateHandler();

    @Override
    public String getBotUsername() {
        return "BChaatt_Bot";
    }

    @Override
    public String getBotToken() {
        return "5054557221:AAGCrXNySIyvbyHyaa6JHwrk7UX1_I_7ObI";
    }

    @Override

    public void onUpdateReceived(Update update) {
        log.Console.printNewRequestInfo(update);
        SendMessage sendMessage;
        sendMessage = this.updateHandler.processUpdate(update);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        String message;
        try {

            if (sendMessage.getText() == null) {
                message = "I'M NOT DONE YET!";
                sendMessage.setText(message);
            }
        } catch (BadInputException e) {
            sendMessage.setText(e.getMessage());
        }
        try {
            execute(sendMessage);
            if (sendMessage instanceof AnonymousLinkCommand){
                SendMessage secondMessage=new SendMessage();
                secondMessage.setText("☝️ پیام بالا رو به دوستات و گروه\u200Cهایی که می\u200Cشناسی فـوروارد کن یا لـینک داخلش رو تو شبکه\u200Cهای اجتماعی بذار و توئیت کن، تا بقیه بتونن بهت پیام ناشناس بفرستن. پیام\u200Cها از طریق همین برنامه بهت می\u200Cرسه.\n" +
                        "\n" +
                        "اینستاگرام داری و میخوای دنبال کننده های اینستاگرامت برات پیام ناشناس بفرستن؟\n" +
                        "پس روی دستور \uD83D\uDC48\uD83C\uDFFB /Instagram کلیک کن!");
                secondMessage.setChatId(update.getMessage().getChatId().toString());
                execute(secondMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

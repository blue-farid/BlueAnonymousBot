package telegram.command;

public class SpecificConnectionCommand extends Command {

    private static final String localMessage = "ربات \" ناشناس \" کاملا انحصاری فقط برای اعضای محترم کانال « رازکام » طراحی شده ، برای استفاده از ربات ، در کانال زیر عضو شوید \uD83D\uDC47\n" +
            "\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\n" +
            "بعد از عضویت در کانال روی « ✅ تایید عضویت » بزنید \uD83D\uDC47\uD83D\uDC47";
    public SpecificConnectionCommand(String chatId) {
        super(chatId);
    }


    @Override
    public void execute() {

    }
}

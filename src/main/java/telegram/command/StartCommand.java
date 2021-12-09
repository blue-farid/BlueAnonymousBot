package telegram.command;

import telegram.MainMenu;

public class StartCommand extends Command {
    public StartCommand(){
        this.setReplyMarkup(new MainMenu().getReplyMarkup());
        this.enableMarkdown(true);
        String message = "حله!\n" +
                "\n" +
                "چه کاری برات انجام بدم؟";
        setText(message);
    }

}

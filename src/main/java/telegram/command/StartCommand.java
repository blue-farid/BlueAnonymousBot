package telegram.command;

public class StartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";

    public StartCommand() {
        super(localMessage);
    }

    public StartCommand(String optionalCommand) {
        super(localMessage, optionalCommand);
    }

    @Override
    public String execute() {
        if (optionalCommand.isEmpty())
            return this.message;
        else {
            return optionalCommand.get();
        }
    }

}

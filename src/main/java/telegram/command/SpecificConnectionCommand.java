package telegram.command;

public class SpecificConnectionCommand extends Command {
    public SpecificConnectionCommand(String chatId) {
        super(chatId);
    }

    public SpecificConnectionCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    @Override
    public void execute() {

    }
}

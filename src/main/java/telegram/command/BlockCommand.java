package telegram.command;

public class BlockCommand extends Command{
    public BlockCommand(String chatId) {
        super(chatId);
    }

    public BlockCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    @Override
    public void execute() {

    }
}

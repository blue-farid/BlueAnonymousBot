package telegram.command;

import utils.RandomUtils;

public class AnonymousLinkCommand extends Command {

    @Override
    public String execute() {
        return null;
    }

    public String generateAnonymousLink() {
        String anonymousLink = "";
        anonymousLink += RandomUtils.getInstance().generateRandomString(2);
        anonymousLink += RandomUtils.getInstance().generateRandomNumber(5);
        anonymousLink += RandomUtils.getInstance().generateRandomString(7);
        return anonymousLink;
    }

}

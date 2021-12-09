package telegram;

enum Command {
    START("/start"), RESTART("/restart");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

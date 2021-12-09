package telegram;

enum Command {
    START("/start"), RESTART("/restart"),
    ANONYMOUS_CONNECTION("\uD83D\uDD17 به یه ناشناس وصلم کن!"),
    SPECIFIC_CONNECTION("\uD83D\uDC8C به مخاطب خاصم وصلم کن!"),
    ANONYMOUS_LINK("لینک ناشناس من\uD83D\uDCEC"),
    ANONYMOUS_TO_GROUP("\uD83D\uDC65 پیام ناشناس به گروه"),
    HELP("راهنما"),
    SCORE("\uD83C\uDFC6 افزایش امتیاز");
    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}

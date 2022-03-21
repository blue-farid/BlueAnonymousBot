package telegram;

public enum Message {
    BAD_INPUT("متوجه نشدم :/\n" +
            "\n" +
            "چه کاری برات انجام بدم؟");

    private final String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

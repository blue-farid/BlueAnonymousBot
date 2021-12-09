package telegram.command;

public class AnonymousLinkCommand extends Command {
    String name;
    private String message;
    public AnonymousLinkCommand(String name){
        this.name=name;
        message="سلام" +
                " " +
                name+
                " هستم ✋️\n" +
                "\n" +
                "لینک زیر رو لمس کن و هر حرفی که تو دلت هست یا هر انتقادی که نسبت به من داری رو با خیال راحت بنویس و بفرست. بدون اینکه از اسمت باخبر بشم پیامت به من می\u200Cرسه. خودتم می\u200Cتونی امتحان کنی و از بقیه بخوای راحت و ناشناس بهت پیام بفرستن، حرفای خیلی جالبی می\u200Cشنوی! \uD83D\uDE09\n" +
                "\n" +
                "\uD83D\uDC47\uD83D\uDC47\n" +
                "https://t.me/BChatBot?start=sc-523805-qHnsfI4";
        setText(message);
    }
}

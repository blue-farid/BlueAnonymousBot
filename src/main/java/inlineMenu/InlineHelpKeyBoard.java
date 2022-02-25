package inlineMenu;

import properties.Property;

public class InlineHelpKeyBoard extends InlineBlueKeyBoard{
   public InlineHelpKeyBoard(){
       addButtonToList(0,"\uD83D\uDC48این روبات چیه؟ به چه درد میخوره؟"+"::"+ Property.COMMANDS_P.get("help.what_for"));
       addButtonToList(1,"\uD83D\uDC48چطوری به یه ناشناس تصادفی وصل بشم؟"+"::"+ Property.COMMANDS_P.get("help.connect_random_anonymous"));
       addButtonToList(2,"\uD83D\uDC48 چطوری نسخه ی رایگان"+"VIP"+" رو فعال کنم؟"+"::"+ Property.COMMANDS_P.get("help.free_vip"));
       addButtonToList(3,"\uD83D\uDC48چطوری به مخاطب خاصم وصل بشم؟"+"::"+ Property.COMMANDS_P.get("help.specific_connection"));
       addButtonToList(4,"\uD83D\uDC48چطوری پیام ناشناس دریافت کنم؟"+"::"+ Property.COMMANDS_P.get("help.receive_anonymous_message"));
       addButtonToList(5,"\uD83D\uDC48چطوری به یه گروه پیام ناشناس بفرستم؟"+"::"+ Property.COMMANDS_P.get("help.send_anonymous_message_group"));
       setInlineBlueKeyBoard();
   }
}

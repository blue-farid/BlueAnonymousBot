package inlineMenu;

import properties.Commands;

/**
 * @author Negar Anabestani
 */

public class InlineHelpKeyBoard extends InlineBlueKeyBoard{
    private static InlineHelpKeyBoard inlineHelpKeyBoard;
    public static InlineHelpKeyBoard getInstance(){
        if (inlineHelpKeyBoard==null)
            inlineHelpKeyBoard=new InlineHelpKeyBoard();
        return inlineHelpKeyBoard;
    }
   private InlineHelpKeyBoard(){
       addButtonToList(0,"\uD83D\uDC48این روبات چیه؟ به چه درد میخوره؟"+"::"+ Commands.HELP_WHAT_FORE.get());
       addButtonToList(1,"\uD83D\uDC48چطوری به یه ناشناس تصادفی وصل بشم؟"+"::"+ Commands.HELP_RANDOM_ANONYMOUS.get());
       addButtonToList(2,"\uD83D\uDC48 چطوری نسخه ی رایگان"+"VIP"+" رو فعال کنم؟"+"::"+ Commands.HELP_FREE_VIP.get());
       addButtonToList(3,"\uD83D\uDC48چطوری به مخاطب خاصم وصل بشم؟"+"::"+ Commands.HELP_SPECIFIC_CONNECTION.get());
       addButtonToList(4,"\uD83D\uDC48چطوری پیام ناشناس دریافت کنم؟"+"::"+ Commands.HELP_RECEIVE_ANONYMOUS_MESSAGE.get());
       addButtonToList(5,"\uD83D\uDC48چطوری به یه گروه پیام ناشناس بفرستم؟"+"::"+ Commands.HELP_ANONYMOUS_TO_GROUP.get());
       setInlineBlueKeyBoard();
   }
}

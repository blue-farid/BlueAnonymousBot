package inlineMenu;

import properties.Property;

public class InlineBackToHelpMainMenuKeyBoard extends InlineBlueKeyBoard{
    private static InlineBackToHelpMainMenuKeyBoard inlineBackToHelpMainMenuKeyBoard;
    public static InlineBackToHelpMainMenuKeyBoard getInstance(){
        if (inlineBackToHelpMainMenuKeyBoard==null)
            inlineBackToHelpMainMenuKeyBoard=new InlineBackToHelpMainMenuKeyBoard();
        return inlineBackToHelpMainMenuKeyBoard;
    }
    private InlineBackToHelpMainMenuKeyBoard(){
        addButtonToList(0,"بازگشت به صفحه راهنما"+"::"+ Property.COMMANDS_P.get("back_help_main_menu"));
    }
}

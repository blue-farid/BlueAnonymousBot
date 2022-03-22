package inlineMenu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * the inlineBlueKeyBoard class the other inlineMenu classes extend it
 * @author Negar Anabestani
 */

public class InlineBlueKeyBoard extends InlineKeyboardMarkup {
    HashMap<Integer,ArrayList<String[]>> keyboardInfo=new HashMap<>();

    /**
     * creates buttons for keyBoard
     * @param text the text shown in button
     * @param callBack the query sent by clicking
     */
    private InlineKeyboardButton creatButton(String text, String callBack){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBack);
        return inlineKeyboardButton;
    }

    /**
     * creates rows for keyBoard
     * @param keyboardInfo a hashMap with int key type for placing the row in the right row number
     *                     and an array of string for value of it with text and callBack string values
     *
     */
    private List<List<InlineKeyboardButton>> creatRows(HashMap<Integer,ArrayList<String[]>> keyboardInfo){
     ArrayList<List<InlineKeyboardButton>> rows=new ArrayList<>();
     for (int h:keyboardInfo.keySet()){
         List<InlineKeyboardButton>currentRow;
         if (h>= rows.size()){
             currentRow=new ArrayList<>();
             rows.add(currentRow);
         }else {
             currentRow=rows.get(h);
         }
         for (String[] s:keyboardInfo.get(h)) {
             currentRow.add(creatButton(s[0],s[1]));
         }
     }
     return rows;
    }

    /**
     * used in other inline classes constructors that extend this class to add button to their keyboard
     * @param rowNumber the row number of the button
     * @param textAndCallBack the text and cllBack value of button
     */
    protected void addButtonToList(int rowNumber,String textAndCallBack){
        if (keyboardInfo.containsKey(rowNumber)){
            ArrayList<String[]> list=keyboardInfo.get(rowNumber);
            list.add(textAndCallBack.split("::"));
            keyboardInfo.replace(rowNumber,list);
        }else {
            ArrayList<String[]> list=new ArrayList<>();
            list.add(textAndCallBack.split("::"));
            keyboardInfo.put(rowNumber,list);
        }
    }
    protected InlineBlueKeyBoard setInlineBlueKeyBoard(){
        this.setKeyboard(creatRows(keyboardInfo));
        return this;
    }
}

package inlineMenu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InlineBlueKeyBoard extends InlineKeyboardMarkup {
    //List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    HashMap<Integer,ArrayList<String[]>> keyboardInfo=new HashMap<>();

    private InlineKeyboardButton creatButton(String text, String callBack){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBack);
        return inlineKeyboardButton;
    }
    private List<List<InlineKeyboardButton>> creatRows(HashMap<Integer,ArrayList<String[]>> keyboardInfo){
     ArrayList<List<InlineKeyboardButton>> rows=new ArrayList<>();
     for (int h:keyboardInfo.keySet()){
         List<InlineKeyboardButton>currentRow;
         if (h>= keyboardInfo.size()){
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

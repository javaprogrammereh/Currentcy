package com.example.currencyconvertnew;

import android.widget.EditText;

public class SplitTextValueToCalc {
    String splitText;
    public Long splitToCalc(EditText text){
        String etValue=text.getText().toString();
        String[] splitvalue = etValue.split(",");
        splitText=convertArrayToStringMethod(splitvalue);
        Long valueFinally =Long.parseLong(splitText);
        return valueFinally;
    }
    public String convertArrayToStringMethod(String[] strArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(strArray[i]);
        }
        return stringBuilder.toString();
    }
}

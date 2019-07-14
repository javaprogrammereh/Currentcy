package com.example.currencyconvertnew;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class splitDigits {
    public String splitDigitsText(Long number) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            DecimalFormatSymbols decimalFormatSymbol = new DecimalFormatSymbols();
            decimalFormatSymbol.setGroupingSeparator(',');
            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
            return decimalFormat.format(number);
        } catch (Exception ex) {
            return String.valueOf(number);
        }
    }

}

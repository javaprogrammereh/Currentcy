package com.example.currencyconvertnew;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.EditText;

public class SizeLengthText {
    int currentLenght;
    public void lenghtSisze(EditText et, Context context) {
        float etSizeByDp = et.getTextSize();
        int etLenght = et.length();
        if (etLenght >=1) {
            int beforeLenght = et.length();
            if (currentLenght < beforeLenght) {
                //   Toast.makeText(MainActivity.this, "bozorrg", Toast.LENGTH_LONG).show();

                etSizeByDp = convertPixelsToDp(etSizeByDp, context);
                etSizeByDp = etSizeByDp - 1;
                et.setTextSize(etSizeByDp);
            } else {
                //   Toast.makeText(MainActivity.this, "koocheck", Toast.LENGTH_LONG).show();
                etSizeByDp = convertPixelsToDp(etSizeByDp, context);
                etSizeByDp = etSizeByDp + 1;
                et.setTextSize(etSizeByDp);
            }
            currentLenght = et.length();
        }
    }
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

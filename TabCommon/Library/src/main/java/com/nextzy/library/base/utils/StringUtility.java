package com.nextzy.library.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.DecimalFormat;

/**
 * Created by thekhaeng on 4/13/2017 AD.
 */

public class StringUtility {

    private static StringUtility instance;

    public static StringUtility getInstance() {
        if (instance == null)
            instance = new StringUtility();
        return instance;
    }


    @NonNull
    public String getCommaNumber(long price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(price);
    }

    @NonNull
    public String getParenthesesNumber(long price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return "("+formatter.format(price)+")";
    }


    @SuppressLint( "DefaultLocale" )
    @NonNull
    public String getFloatString(float rating, int digit) {
        return String.format("%." + digit + "f", rating);
    }

    @SuppressLint( "DefaultLocale" )
    @NonNull
    public static String createTimeString( Context context, int hour24, int minute){
        String timeString;
        if ( DateFormat.is24HourFormat(context)) {
            timeString = String.format("%02d:%02d", hour24, minute);
        } else {
            if (hour24 > 12) {
                timeString = String.format("%02d:%02d %s", hour24,minute, "pm");
            } else {
                timeString = String.format("%02d:%02d %s", hour24 % 12,minute, "am");
            }
        }
        return timeString;
    }
}

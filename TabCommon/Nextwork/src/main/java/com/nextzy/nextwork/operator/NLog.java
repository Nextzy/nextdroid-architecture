package com.nextzy.nextwork.operator;

import android.util.Log;

import com.nextzy.nextwork.BuildConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NLog{
    private final static String TAG = "Nextwork";
    private static boolean showLog = BuildConfig.DEBUG;

    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";

    public static final int NUMBER_OF_SPACE = 2;
    private static final int CALL_STACK_INDEX = 2;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile( "(\\$\\d+)+$" );


    public static void setShowLog( boolean showLog ){
        NLog.showLog = showLog;
    }

    public static void i( String tag, String message ){
        if( showLog ){
            Log.i( TAG, prependCallLocation( TOP_LEFT_CORNER + DOUBLE_DIVIDER ) );
            Log.i( TAG, prependCallLocation( HORIZONTAL_LINE + " " + message ) );
            Log.i( TAG, prependCallLocation( BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER ) );
        }
    }

    public static void e( String tag, String message ){
        if( showLog ){
            Log.e( TAG, prependCallLocation( TOP_LEFT_CORNER + DOUBLE_DIVIDER ) );
            Log.e( TAG, prependCallLocation( HORIZONTAL_LINE + " " + message ) );
            Log.e( TAG, prependCallLocation( BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER ) );
        }
    }

    public static void e( String tag, String serviceName, Throwable throwable ){
        if( showLog ){
            Log.i( TAG, prependCallLocation( TOP_LEFT_CORNER + DOUBLE_DIVIDER ) );
            Log.i( TAG, prependCallLocation( HORIZONTAL_LINE + " Throwable: " + throwable.getClass().getSimpleName() ) );
            Log.i( TAG, prependCallLocation( HORIZONTAL_LINE + " Message: " + throwable.getMessage() ) );
            Log.i( TAG, prependCallLocation( MIDDLE_CORNER + SINGLE_DIVIDER ) );
            Log.i( TAG, prependCallLocation( HORIZONTAL_LINE + " Service: " + serviceName ) );
            Log.i( TAG, prependCallLocation( BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER ) );
        }
    }

    private static String prependCallLocation( String message ){
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if( stackTrace.length <= CALL_STACK_INDEX ){
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?" );
        }
        String clazz = extractClassName( stackTrace[CALL_STACK_INDEX] );
        int lineNumber = stackTrace[CALL_STACK_INDEX].getLineNumber();
        String space = "";
        if( Integer.toString( lineNumber ).length() < NUMBER_OF_SPACE + 1 ){
            int numberOfSpace = NUMBER_OF_SPACE + 1 - Integer.toString( lineNumber ).length();
            space = String.format( "%" + numberOfSpace + "s", "" );
        }
        return ".(" + clazz + ":" + lineNumber + ")" + space + "- " + message;
    }

    /**
     * Extract the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     */
    private static String extractClassName( StackTraceElement element ){
        String tag = element.getFileName();
        Matcher m = ANONYMOUS_CLASS.matcher( tag );
        if( m.find() ){
            tag = m.replaceAll( "" );
        }
        return tag;
    }


}

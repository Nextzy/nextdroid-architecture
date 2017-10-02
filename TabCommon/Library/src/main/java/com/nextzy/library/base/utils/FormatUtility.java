package com.nextzy.library.base.utils;

/**
 * Created by TheKhaeng on 8/9/16 AD.
 */
public class FormatUtility {
    public static final String DEFAULT_SEPARATE = "-";
    public static final String PATTERN_MOBILE_NUMBER = "###-###-####";
    public static final String PATTERN_ID_CARD = "#-####-#####-##-#";

    public static String applyStringPattern(String text, String format) {
        return FormatUtility.applyStringPattern(text, format, DEFAULT_SEPARATE);
    }

    public static String applyStringPattern(String text, String format, String separate) {
        // ex. pattern "(\\d{3})(\\d{3})(\\d+)"
        String pattern = "";
        // ex. replacement "$1-$2-$3"
        String replacement = "";
        String[] formats = format.split(separate);
        for (int i = 0; i < formats.length; i++) {
            pattern += "(\\d{" + formats[i].length() + "})";
            if (i == 0) {
                replacement += "$" + (i + 1);
            } else {
                replacement += separate + "$" + (i + 1);
            }
        }
        text = text.replaceFirst(pattern, replacement);
        return text;
    }

    public static String getTextNoSeparate(String text) {
        return getTextNoSeparate(text,DEFAULT_SEPARATE);
    }

    public static String getTextNoSeparate(String text, String separate) {
        if (text.contains(separate)) {
            return text.replace(separate, "");
        }
        return text;
    }
}


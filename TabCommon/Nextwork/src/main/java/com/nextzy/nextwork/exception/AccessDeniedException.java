package com.nextzy.nextwork.exception;

/**
 * Created by TheKhaeng on 8/19/2016.
 */
public class AccessDeniedException extends NullPointerException {
    public AccessDeniedException() {
    }

    public AccessDeniedException(String s) {
        super(s);
    }
}

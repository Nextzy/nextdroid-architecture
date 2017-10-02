package com.nextzy.nextwork.exception;

/**
 * Created by TheKhaeng on 8/19/2016.
 */
public class SessionExpireException extends NullPointerException {
    public SessionExpireException() {
    }

    public SessionExpireException(String s) {
        super(s);
    }
}

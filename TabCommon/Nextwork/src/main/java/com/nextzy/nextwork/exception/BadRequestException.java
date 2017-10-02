package com.nextzy.nextwork.exception;

/**
 * Created by TheKhaeng on 8/19/2016.
 */
public class BadRequestException extends NullPointerException {
    public BadRequestException() {
    }

    public BadRequestException(String s) {
        super(s);
    }
}

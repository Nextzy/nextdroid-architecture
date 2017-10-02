package com.nextzy.nextwork.exception;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
public class BadRequestException extends NullPointerException {
    public BadRequestException() {
    }

    public BadRequestException(String s) {
        super(s);
    }
}

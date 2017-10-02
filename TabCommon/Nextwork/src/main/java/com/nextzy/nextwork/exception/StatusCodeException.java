package com.nextzy.nextwork.exception;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class StatusCodeException extends Exception {

    private int statusCode;

    public StatusCodeException(int code) {
        super("Status code: "+code);
        this.statusCode = code;
    }

    public StatusCodeException(int code, String message) {
        super("Status code: "+code + " " + message.trim());
        this.statusCode = code;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

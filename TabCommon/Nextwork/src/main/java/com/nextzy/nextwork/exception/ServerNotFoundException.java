package com.nextzy.nextwork.exception;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
public class ServerNotFoundException extends StatusCodeException {

    public ServerNotFoundException(int code) {
        super(code, "for Not found requests, when a resource can't be found to fulfill the request");
    }

}

package com.nextzy.nextwork.exception;

/**
 * Created by TheKhaeng on 9/16/2016.
 */
public class ServerNotFoundException extends StatusCodeException {

    public ServerNotFoundException(int code) {
        super(code, "for Not found requests, when a resource can't be found to fulfill the request");
    }

}

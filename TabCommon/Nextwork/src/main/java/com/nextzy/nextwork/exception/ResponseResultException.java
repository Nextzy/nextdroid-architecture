package com.nextzy.nextwork.exception;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class ResponseResultException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ResponseResultException(Class<?> resultClass, String message) {
        super(resultClass.getSimpleName() + " Result is not valid."
                + "\nmessage: " + message);
    }
}

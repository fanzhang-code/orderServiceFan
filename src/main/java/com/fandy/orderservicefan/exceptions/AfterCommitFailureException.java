package com.fandy.orderservicefan.exceptions;

public class AfterCommitFailureException extends RuntimeException {

    public AfterCommitFailureException(String message) {
        super(message);
    }
}

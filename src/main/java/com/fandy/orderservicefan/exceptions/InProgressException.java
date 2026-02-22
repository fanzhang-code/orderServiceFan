package com.fandy.orderservicefan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_EARLY)
public class InProgressException extends RuntimeException {

    public InProgressException(String message) {
        super(message);
    }
}


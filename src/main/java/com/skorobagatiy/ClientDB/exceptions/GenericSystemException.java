package com.skorobagatiy.ClientDB.exceptions;

import org.springframework.http.HttpStatus;

public class GenericSystemException extends Exception{
    private HttpStatus httpStatus;
    public GenericSystemException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

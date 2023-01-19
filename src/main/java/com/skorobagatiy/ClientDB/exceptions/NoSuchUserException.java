package com.skorobagatiy.ClientDB.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends GenericSystemException{
    public NoSuchUserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}

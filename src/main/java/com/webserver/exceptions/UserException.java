package com.webserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Marin on 6/18/2017.
 */
@ResponseStatus(value= HttpStatus.CONFLICT, reason="Data integrity violation")  // 409
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

}

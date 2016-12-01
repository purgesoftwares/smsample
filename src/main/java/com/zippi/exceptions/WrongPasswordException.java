package com.zippi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class WrongPasswordException extends Exception {
	
    private static final long serialVersionUID = 5861310537366287163L;
    
    public WrongPasswordException() {
    	super();
    }
    
    public WrongPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongPasswordException(final String message) {
        super(message);
    }

    public WrongPasswordException(final Throwable cause) {
        super(cause);
    }

}

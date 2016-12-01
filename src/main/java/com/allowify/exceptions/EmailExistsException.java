package com.allowify.exceptions;


/**
 * 
 * @author Bharat
 *
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(final String message) {
        super(message);
    }

}


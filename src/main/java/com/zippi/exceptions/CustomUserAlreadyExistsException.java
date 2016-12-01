package com.zippi.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author shankarp
 *
 */
@ResponseStatus(value=HttpStatus.CONFLICT)
public final class CustomUserAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 5861310537366287163L;

    public CustomUserAlreadyExistsException() {
        super();
    }

    public CustomUserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomUserAlreadyExistsException(final String message) {
        super(message);
    }

    public CustomUserAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}

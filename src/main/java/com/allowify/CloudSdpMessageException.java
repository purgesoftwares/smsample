package com.allowify;

import java.util.List;

import org.springframework.validation.Errors;

public class CloudSdpMessageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<Errors> formattedErrors;
    /**
     * Constructor.
     */
    public CloudSdpMessageException() {
        super();
    }
    /**
     * Constructor.
     * @param msg message string
     */
    public CloudSdpMessageException(String msg) {
        super(msg);
    }
    /**
     * Constructor.
     * @param e {@link Throwable}
     */
    public CloudSdpMessageException(Throwable e) {
        super(e);
    }
    /**
     * Constructor.
     * @param msg message string
     * @param e {@link Throwable}
     */
    public CloudSdpMessageException(String msg, Throwable e) {
        super(msg, e);
    }
    /**
     * Set list of Errors objects.
     *
     * @param fe the formatted errors
     */
    public void setFormattedErrors(
      List<Errors> fe) {
        this.formattedErrors = fe;
    }
    /**
     * Get list of Errors objects.
     *
     * @return  Collection of IIPObjectErrors
     */
    public List<Errors> getFormattedErrors() {
        return formattedErrors;
    }
}
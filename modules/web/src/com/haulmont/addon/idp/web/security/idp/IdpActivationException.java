package com.haulmont.addon.idp.web.security.idp;

public class IdpActivationException extends Exception {

    public IdpActivationException(String message) {
        super(message);
    }

    public IdpActivationException(Throwable cause) {
        super(cause);
    }

    public IdpActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
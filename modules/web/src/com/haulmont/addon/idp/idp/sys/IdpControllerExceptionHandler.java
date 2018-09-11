package com.haulmont.addon.idp.idp.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Logs all exceptions of IDP controllers and returns 500 (Internal Server Error) error page to clients.
 */
@ControllerAdvice("com.haulmont.addon.idp.controllers")
@Component("cuba_IdpControllerExceptionHandler")
public class IdpControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(IdpControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Exception in MVC controller", ex);

        return new ResponseEntity<>("IDP server error, please see server log for details",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
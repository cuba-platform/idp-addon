package com.haulmont.addon.idp.restapi.controllers;

import com.haulmont.cuba.core.sys.ConditionalOnAppProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@ConditionalOnAppProperty(property = "cuba.rest.idp.enabled", value = "true")
@RestController
@RequestMapping("/v2/idp/idpc")
public class IdpLogoutCallbackController {

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(@RequestParam(name = "idpSessionId") String idpSessionId,
                       @RequestParam(name = "trustedServicePassword") String trustedServicePassword,
                       HttpServletResponse response) {
        // synchronous logout of REST API client is not supported yet
    }
}
package com.haulmont.addon.idp.idp.controllers;

import com.haulmont.addon.idp.idp.config.IdpConfig;
import com.haulmont.addon.idp.idp.sys.IdpServiceLogoutCallbackInvoker;
import com.haulmont.addon.idp.security.IdpService;
import com.haulmont.addon.idp.security.global.IdpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Endpoint for server-to-server interaction between service providers and IDP.
 */
@RestController("idp_IdpServiceController")
@RequestMapping(value = "service", produces = "application/json; charset=UTF-8")
public class IdpServiceController {

    private static final Logger log = LoggerFactory.getLogger(IdpServiceController.class);

    @Inject
    protected IdpService idpService;
    @Inject
    protected IdpConfig idpConfig;
    @Inject
    protected IdpServiceLogoutCallbackInvoker logoutCallbackInvoker;

    @RequestMapping(value = "activate", method = RequestMethod.POST)
    public IdpSession activateServiceProviderTicket(@RequestParam("serviceProviderTicket") String serviceProviderTicket,
                                                    @RequestParam("trustedServicePassword") String trustedServicePassword,
                                                    HttpServletResponse response) {
        if (!Objects.equals(idpConfig.getTrustedServicePassword(), trustedServicePassword)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            log.warn("Incorrect trusted client password has been passed {}", trustedServicePassword);
            return null;
        }

        log.debug("Activate service provider ticket {}", serviceProviderTicket);

        IdpSession idpSession = idpService.activateServiceProviderTicket(serviceProviderTicket);

        if (idpSession == null) {
            log.debug("IDP Session not found for ticket {}", serviceProviderTicket);
            response.setStatus(HttpStatus.GONE.value());
            return null;
        }

        log.debug("IDP ticket {} activated for session {}", serviceProviderTicket, idpSession);

        return idpSession;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public IdpSession getSession(@RequestParam("idpSessionId") String idpSessionId,
                                 @RequestParam("trustedServicePassword") String trustedServicePassword,
                                 HttpServletResponse response) {
        if (!Objects.equals(idpConfig.getTrustedServicePassword(), trustedServicePassword)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            log.warn("Incorrect trusted client password has been passed {}", trustedServicePassword);
            return null;
        }

        log.debug("Get IDP session {}", idpSessionId);

        IdpSession idpSession = idpService.getSession(idpSessionId);
        if (idpSession == null) {
            log.debug("IDP Session not found for id {}", idpSessionId);
            response.setStatus(HttpStatus.GONE.value());
            return null;
        }

        log.debug("IDP session {} obtained", idpSession);

        return idpSession;
    }

    @RequestMapping(value = "ping", method = RequestMethod.POST)
    public void pingSession(@RequestParam("idpSessionId") String idpSessionId,
                            @RequestParam("trustedServicePassword") String trustedServicePassword,
                            HttpServletResponse response) {
        if (!Objects.equals(idpConfig.getTrustedServicePassword(), trustedServicePassword)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            log.warn("Incorrect trusted client password has been passed {}", trustedServicePassword);
            return;
        }

        log.debug("Ping IDP session {}", idpSessionId);

        IdpSession idpSession = idpService.getSession(idpSessionId);
        if (idpSession == null) {
            log.debug("IDP Session not found for id {}", idpSessionId);
            response.setStatus(HttpStatus.GONE.value());
        }

        log.debug("IDP session {} ping successful", idpSession);
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public void removeSession(@RequestParam("idpSessionId") String idpSessionId,
                              @RequestParam("trustedServicePassword") String trustedServicePassword,
                              HttpServletResponse response) {
        if (!Objects.equals(idpConfig.getTrustedServicePassword(), trustedServicePassword)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            log.warn("Incorrect trusted client password has been passed {}", trustedServicePassword);
            return;
        }

        log.debug("Logout IDP session {}", idpSessionId);

        boolean loggedOut = idpService.logout(idpSessionId);

        if (loggedOut) {
            log.info("Logged out IDP session {}", idpSessionId);

            logoutCallbackInvoker.performLogoutOnServiceProviders(idpSessionId);
        }
    }
}
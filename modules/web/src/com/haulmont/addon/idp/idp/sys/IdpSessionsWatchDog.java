package com.haulmont.addon.idp.idp.sys;

import com.haulmont.addon.idp.idp.config.IdpConfig;
import com.haulmont.addon.idp.security.IdpService;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.TrustedClientService;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.security.global.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Invokes IdpService to check if IDP sessions or tickets are expired. <br>
 * Performs request to logout URLs of service providers registered in {@link IdpConfig#getServiceProviderLogoutUrls()}.
 */
@Component("cuba_IdpSessionsWatchDog")
public class IdpSessionsWatchDog {

    private static final Logger log = LoggerFactory.getLogger(IdpSessionsWatchDog.class);

    @Inject
    protected TrustedClientService trustedClientService;

    @Inject
    protected IdpService idpService;

    @Inject
    protected IdpConfig idpConfig;

    @Inject
    protected IdpServiceLogoutCallbackInvoker logoutCallbackInvoker;

    public void cleanupExpiredSessions() {
        if (!AppContext.isStarted()) {
            return;
        }

        List<String> serviceProviderUrls = idpConfig.getServiceProviderUrls();
        if (serviceProviderUrls.isEmpty()) {
            // there are no service providers registered
            return;
        }

        UserSession systemSession;
        try {
            systemSession = trustedClientService.getSystemSession(idpConfig.getTrustedClientPassword());
        } catch (LoginException e) {
            log.error("Unable to obtain system session", e);
            return;
        }

        AppContext.withSecurityContext(new SecurityContext(systemSession), () -> {
            List<String> loggedOutIdpSessionIds = idpService.processEviction(
                    idpConfig.getSessionExpirationTimeoutSec(),
                    idpConfig.getTicketExpirationTimeoutSec()
            );

            for (String idpSessionId : loggedOutIdpSessionIds) {
                log.debug("IDP Session {} expired. Logout from service providers");

                logoutCallbackInvoker.performLogoutOnServiceProviders(idpSessionId);
            }
        });
    }
}
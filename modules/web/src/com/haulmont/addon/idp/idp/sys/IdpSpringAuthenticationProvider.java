package com.haulmont.addon.idp.idp.sys;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Used only as stub for Spring Security configuration.
 */
@Component("cuba_IdpSpringAuthenticationProvider")
public class IdpSpringAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // we do not use authentication using authentication provider
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
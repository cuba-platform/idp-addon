package com.haulmont.addon.idp.web.security.idp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;
import java.util.Locale;

public class IdpServletRequestWrapper extends HttpServletRequestWrapper {

    private final IdpSessionPrincipalImpl principal;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public IdpServletRequestWrapper(HttpServletRequest request, IdpSessionPrincipalImpl principal) {
        super(request);
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public Locale getLocale() {
        if (principal.getLocale() != null) {
            return principal.getLocale();
        }

        return super.getLocale();
    }
}
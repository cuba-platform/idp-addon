package com.haulmont.addon.idp.web.security.idp;

import com.haulmont.addon.idp.security.global.IdpSession;

import javax.annotation.Nullable;
import java.security.Principal;
import java.util.Locale;

public class IdpSessionPrincipalImpl implements Principal, IdpSessionPrincipal {

    protected final IdpSession idpSession;

    public IdpSessionPrincipalImpl(IdpSession idpSession) {
        this.idpSession = idpSession;
    }

    @Override
    public String getName() {
        return idpSession.getLogin();
    }

    @Override
    public IdpSession getIdpSession() {
        return idpSession;
    }

    @Nullable
    public Locale getLocale() {
        String locale = idpSession.getLocale();
        if (locale == null) {
            return null;
        }

        return Locale.forLanguageTag(locale);
    }
}
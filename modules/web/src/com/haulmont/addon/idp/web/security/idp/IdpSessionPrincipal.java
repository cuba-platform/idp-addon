package com.haulmont.addon.idp.web.security.idp;

import com.haulmont.addon.idp.security.global.IdpSession;

public interface IdpSessionPrincipal {

    String IDP_SESSION_ATTRIBUTE = "IDP_SESSION";
    String IDP_SESSION_LOCK_ATTRIBUTE = "IDP_SESSION_LOCK";
    String IDP_TICKET_REQUEST_PARAM = "idp_ticket";

    IdpSession getIdpSession();
}
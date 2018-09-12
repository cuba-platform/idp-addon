package com.haulmont.addon.idp.idp.controllers;

/**
 * Available IDP ticket response types.
 */
public enum ResponseType {

    /**
     * IDP ticket is sent using URL hash: {@code //service-provider-url#idp_ticket=value}
     */
    CLIENT_TICKET("client-ticket"),
    /**
     * IDP ticket is sent using URL parameter: {@code //service-provider-url?idp_ticket=value}
     */
    SERVER_TICKET("server-ticket");

    protected String code;

    ResponseType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
package com.haulmont.addon.idp.idp.model;

public class IdpTicket {

    protected String idpTicket;

    public IdpTicket(String idpTicket) {
        this.idpTicket = idpTicket;
    }

    public String getIdpTicket() {
        return idpTicket;
    }

    public void setIdpTicket(String idpTicket) {
        this.idpTicket = idpTicket;
    }
}
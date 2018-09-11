package com.haulmont.addon.idp.restapi.model;

public class IdpLogoutResponse {

    protected String location;

    public IdpLogoutResponse() {
    }

    public IdpLogoutResponse(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
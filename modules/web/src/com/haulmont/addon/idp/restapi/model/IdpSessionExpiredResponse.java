package com.haulmont.addon.idp.restapi.model;

public class IdpSessionExpiredResponse {

    protected String error;
    protected String location;

    public IdpSessionExpiredResponse() {
    }

    public IdpSessionExpiredResponse(String error, String location) {
        this.location = location;
        this.error = error;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
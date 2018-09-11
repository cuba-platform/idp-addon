package com.haulmont.addon.idp.idp.model;

import java.io.Serializable;

public class AuthResponse implements Serializable {

    protected String errorCode;
    protected String serviceProviderUrl;

    public AuthResponse() {
    }

    public static AuthResponse authenticated(String serviceProviderUrl) {
        AuthResponse response = new AuthResponse();
        response.setServiceProviderUrl(serviceProviderUrl);
        return response;
    }

    public static AuthResponse failed(String errorCode) {
        AuthResponse response = new AuthResponse();
        response.setErrorCode(errorCode);
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getServiceProviderUrl() {
        return serviceProviderUrl;
    }

    public void setServiceProviderUrl(String serviceProviderUrl) {
        this.serviceProviderUrl = serviceProviderUrl;
    }
}
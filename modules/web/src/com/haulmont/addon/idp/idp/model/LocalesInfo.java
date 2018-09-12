package com.haulmont.addon.idp.idp.model;

import java.io.Serializable;
import java.util.Map;

public class LocalesInfo implements Serializable {

    protected boolean localeSelectVisible;

    protected Map<String, String> locales;

    public boolean isLocaleSelectVisible() {
        return localeSelectVisible;
    }

    public void setLocaleSelectVisible(boolean localeSelectVisible) {
        this.localeSelectVisible = localeSelectVisible;
    }

    public Map<String, String> getLocales() {
        return locales;
    }

    public void setLocales(Map<String, String> locales) {
        this.locales = locales;
    }
}
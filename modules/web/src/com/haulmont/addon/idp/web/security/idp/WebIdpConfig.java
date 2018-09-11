package com.haulmont.addon.idp.web.security.idp;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultBoolean;

/**
 * Configuration parameters interface used by IDP SSO login provider.
 */
@Source(type = SourceType.APP)
public interface WebIdpConfig extends Config {

    /**
     * @return whether IDP authentication for Web Client is enabled or not
     */
    @Property("cuba.web.idp.enabled")
    @DefaultBoolean(false)
    boolean getIdpEnabled();

    /**
     * @return Base URL of IDP server, e.g. http://localhost:8080/app/idp.
     */
    @Property("cuba.web.idp.baseUrl")
    String getIdpBaseURL();

    /**
     * @return Trusted password to access to IDP server.
     */
    @Property("cuba.web.idp.trustedServicePassword")
    String getIdpTrustedServicePassword();
}
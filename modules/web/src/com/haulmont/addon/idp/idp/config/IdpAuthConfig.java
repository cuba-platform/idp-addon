/*
 * Copyright (c) 2008-2018 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.idp.idp.config;

import com.haulmont.cuba.core.config.*;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultString;
import com.haulmont.cuba.core.config.type.CommaSeparatedStringListTypeFactory;
import com.haulmont.cuba.core.config.type.Factory;

import java.util.List;

@Source(type = SourceType.APP)
public interface IdpAuthConfig extends Config {

    /**
     * @return mode of authentication: STANDARD or LDAP
     */
    @Property("cuba.idp.authenticationMode")
    @Default("STANDARD")
    @EnumStore(EnumStoreMode.NAME)
    IdpAuthMode getAuthenticationMode();

    /* LDAP integration */

    /**
     * @return the urls of the LDAP servers
     */
    @Property("cuba.idp.ldap.urls")
    @Factory(factory = CommaSeparatedStringListTypeFactory.class)
    List<String> getLdapUrls();

    /**
     * @return the base LDAP suffix from which all operations should origin.
     * If a base suffix is set, you will not have to (and, indeed, must not) specify the full distinguished names in any
     * operations performed. For instance: dc=example,dc=com
     */
    @Property("cuba.idp.ldap.base")
    String getLdapBase();

    /**
     * @return user that is used to connect to LDAP server.
     * For instance: cn=System User,ou=Employees,dc=mycompany,dc=com
     */
    @Property("cuba.idp.ldap.user")
    String getLdapUser();

    /**
     * @return password that is used to connect to LDAP server
     */
    @Property("cuba.idp.ldap.password")
    String getLdapPassword();

    /**
     * @return Field of LDAP object for user login matching.
     */
    @Property("cuba.idp.ldap.userLoginField")
    @DefaultString("sAMAccountName")
    String getLdapUserLoginField();

    /**
     * @return list of users that are not allowed to use external authentication. They can use only standard authentication.
     *         Empty list means that everyone is allowed to login using external authentication.
     *
     * @see #getAuthenticationMode()
     */
    @Property("cuba.idp.standardAuthenticationUsers")
    @Factory(factory = CommaSeparatedStringListTypeFactory.class)
    List<String> getStandardAuthenticationUsers();
}
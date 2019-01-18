# CUBA IDP Add-on

## Overview

Single-sign-on (SSO) for CUBA applications allows a user to log in to the multiple 
running applications by entering a single login name and password once 
in a browser session.

![IDP Login Form](./img/idp_login_form.png)

When using SSO, there are two types of applications:

- **Identity Provider (IDP)** is an application that provides user authentication. 
It contains a login form for entering user credentials and checks the credentials 
against the list of registered users. Only one Identity Provider is allowed in 
an SSO environment.

- **Service Provider (SP)** is a regular application that redirects to IDP for 
user authentication. SP should contain the same list of users as IDP 
(passwords do not matter though). SP provides authorization using CUBA 
security roles and access groups. There may be any number of Service Providers in 
an SSO environment.

An application can be an Identity Provider and a Service Provider at the same time,
so you don’t have to setup a dedicated IDP.

> CUBA SSO uses custom HTTP-based protocol and currently does not provide 
integration with systems using standard authentication protocols like SAML or OIDC.

In SSO environment, when a user enters a Service Provider URL, the SP redirects 
to the IDP page for entering login name and password. After successful 
authentication, IDP redirects back to the SP application and the user 
transparently logs in to SP.

Read addon Wiki for an additional information: [link](https://github.com/cuba-platform/idp-addon/wiki).


## Compatibility with platform versions

| Add-on        | Platform      |
|:------------- |:------------- |
| 0.1.0         | 7.0.0         |

## Installation and configuration

1. Add **idp-addon** as an application component to your projects 
(change the version part if needed):

> `com.haulmont.addon.idp:idp-global:0.1.0`

2. Configure add-on with application properties 
([Wiki](https://github.com/cuba-platform/idp-addon/wiki#identity-provider-sso-setup))

## Demo

This section is a short version of example from
[Wiki](https://github.com/cuba-platform/idp-addon/wiki/Single-Sign-On-Example).

We consider an example of setting up SSO for two applications: 
Fish and Chips. Fish will be an Identity Provider and Service Provider 
at the same time, Chips will be a Service Provider.

1. Add the **idp-addon** via Studio with the following coordinates:

> `com.haulmont.addon.idp:idp-global:0.1.0`

2. Configure aliases in the `hosts` file:

| Address       | Alias         |
|:------------- |:------------- |
| 127.0.0.1     | fish          |
| 127.0.0.1     | chips         |

3. Configure IDP for the Fish project in `web-app.properties` file of the `web` 
module:

```
cuba.webAppUrl = http://fish:8081/app/

cuba.idp.serviceProviderUrls = http://fish:8081/app/,http://chips:8082/app/
cuba.idp.serviceProviderLogoutUrls = http://fish:8081/app/dispatch/idpc/logout,http://chips:8082/app/dispatch/idpc/logout
cuba.idp.trustedServicePassword = mdgh12SSX_pic2

cuba.web.idp.enabled = true
cuba.web.idp.baseUrl = http://fish:8081/app/idp/
cuba.web.idp.trustedServicePassword = mdgh12SSX_pic2
```

4. Configure IDP for the "Chips" project in `web-app.properties` file of the `web` 
module:

```
cuba.webAppUrl = http://chips:8082/app/

cuba.web.idp.enabled = true
cuba.web.idp.baseUrl = http://fish:8081/app/idp/
cuba.web.idp.trustedServicePassword = mdgh12SSX_pic2
```

5. Start the Fish server by launching its `tomcat/bin/startup.*` script or
via Gradle: `gradlew start`.

6. Go to [http://fish:8081/app/](http://fish:8081/app/) in your web browser. 
You will be redirected to the IDP login page. Log in with the `admin / admin` 
credentials. Create a new user, for example `u1`.

7. Start the Chips server by launching its `tomcat/bin/startup.*` script or
via Gradle: `gradlew start`.

8. Go to [http://chips:8082/app/](http://chips:8082/app/) in the same web browser. 
If you are still logged in to the Fish application, you will be automatically 
logged in as admin to Chips. Create the same u1 user (password does not matter) 
in the `Chips` application.

Now you can log in as `admin` or `u1` to both applications via the single login form,
and if you are logged in one application, the login process for the second 
application will be automatic, bypassing the login form.

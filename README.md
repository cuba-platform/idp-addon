# CUBA IDP Add-on

[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/cuba-platform/idp-addon.svg?branch=master)](https://travis-ci.org/cuba-platform/idp-addon)
[![Documentation](https://img.shields.io/badge/documentation-online-03a9f4.svg)](https://github.com/cuba-platform/idp-addon/wiki)

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

## Installation

The add-on can be added to your project in one of the ways described below. Installation from the Marketplace is the simplest way. The last version of the add-on compatible with the used version of the platform will be installed.
Also, you can install the add-on by coordinates choosing the required version of the add-on from the table.

In case you want to install the add-on by manual editing or by building from sources see the complete add-ons installation guide in [CUBA Platform documentation](https://doc.cuba-platform.com/manual-latest/manual.html#app_components_usage).

### From the Marketplace

1. Open your application in CUBA Studio. Check the latest version of CUBA Studio on the [CUBA Platform site](https://www.cuba-platform.com/download/previous-studio/).
2. Go to *CUBA -> Marketplace* in the main menu.

 ![marketplace](img/marketplace.png)

3. Find the *IDP* add-on there.

 ![addons](img/addons.png)

4. Click *Install* and apply the changes.
The add-on corresponding to the used platform version will be installed.

### By Coordinates

1. Open your application in CUBA Studio. Check the latest version of CUBA Studio on the [CUBA Platform site](https://www.cuba-platform.com/download/previous-studio/).
2. Go to *CUBA -> Marketplace* in the main menu.
3. Click the icon in the upper-right corner.

 ![by-coordinates](img/by-coordinates.png)

4. Paste the add-on coordinates in the corresponding field as follows:

 `com.haulmont.addon.idp:idp-global:<add-on version>`

 where `<add-on version>` is compatible with the used version of the CUBA platform.

 | Platform Version | Add-on Version |
|------------------|----------------|
| 7.1.X            | 0.2.0          |
| 7.0.X            | 0.1.1          |

5. Click *Install* and apply the changes. The add-on will be installed to your project.

## Configuration

Configure add-on with application properties
([Wiki](https://github.com/cuba-platform/idp-addon/wiki#identity-provider-sso-setup))

## Demo

This section is a short version of example from
[Wiki](https://github.com/cuba-platform/idp-addon/wiki/Single-Sign-On-Example).

We consider an example of setting up SSO for two applications:
Fish and Chips. Fish will be an Identity Provider and Service Provider
at the same time, Chips will be a Service Provider.

1. Add the **idp-addon** via Studio with the following coordinates (replace x with bug-fix number):

    `com.haulmont.addon.idp:idp-global:0.2.0`

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

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
 *
 */

package com.haulmont.addon.idp.idp.sys;

import com.haulmont.cuba.core.sys.AbstractWebAppContextLoader;
import com.haulmont.cuba.core.sys.servlet.ServletRegistrationManager;
import com.haulmont.cuba.core.sys.servlet.events.ServletContextInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.inject.Inject;
import javax.servlet.*;
import java.util.EnumSet;

@Component(IdpInitializer.NAME)
public class IdpInitializer {

    public static final String NAME = "idp_IdpInitializer";

    protected static final String IDP_SERVLET_NAME = "idp";

    @Inject
    protected ServletRegistrationManager servletRegistrationManager;

    @EventListener
    protected void init(ServletContextInitializedEvent event) {
        ApplicationContext appCtx = event.getApplicationContext();
        Servlet idpServlet = servletRegistrationManager.createServlet(appCtx, IdpServlet.class.getName());

        ServletContext servletCtx = event.getSource();
        try {
            idpServlet.init(new AbstractWebAppContextLoader.CubaServletConfig(IDP_SERVLET_NAME, servletCtx));
        } catch (ServletException e) {
            throw new RuntimeException("An error occurred while initializing idp servlet", e);
        }

        ServletRegistration.Dynamic idpServletRegistration = servletCtx.addServlet(IDP_SERVLET_NAME, idpServlet);
        idpServletRegistration.setLoadOnStartup(4);
        idpServletRegistration.addMapping("/idp/*");

        DelegatingFilterProxy idpSpringSecurityFilterChain = new DelegatingFilterProxy();
        idpSpringSecurityFilterChain.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.idp");
        idpSpringSecurityFilterChain.setTargetBeanName("springSecurityFilterChain");

        FilterRegistration.Dynamic idpSpringSecurityFilterChainReg =
                servletCtx.addFilter("idpSpringSecurityFilterChain", idpSpringSecurityFilterChain);

        idpSpringSecurityFilterChainReg.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/idp/*");
    }
}

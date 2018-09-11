package com.haulmont.addon.idp.core.jmx;

import com.haulmont.addon.idp.security.IdpSessionStore;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.List;

/**
 * JMX interface for {@link IdpSessionStore}
 */
@ManagedResource(description = "Manages identity provider sessions")
public interface IdpSessionsMBean {

    @ManagedOperation(description = "Print session info")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "sessionId", description = "")})
    String getSessionInfo(String sessionId);

    @ManagedOperation(description = "Activate service provider ticket and print session info")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "serviceProviderTicket", description = "")})
    String activateSessionTicket(String serviceProviderTicket);

    @ManagedOperation(description = "Create service provider ticket for existing session")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "sessionId", description = "")})
    String createServiceProviderTicket(String sessionId);

    @ManagedOperation(description = "Remove existing session")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "sessionId", description = "")})
    String removeSession(String sessionId);

    @ManagedOperation(description = "Print all session ids")
    List<String> getSessions();

    @ManagedOperation(description = "Print all non activated service provider tickets")
    List<String> getTickets();
}
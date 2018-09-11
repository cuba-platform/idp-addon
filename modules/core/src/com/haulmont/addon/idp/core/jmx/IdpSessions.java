package com.haulmont.addon.idp.core.jmx;

import com.google.gson.Gson;
import com.haulmont.addon.idp.security.IdpSessionStore;
import com.haulmont.addon.idp.security.global.IdpSession;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("cuba_IdpSessionsMBean")
public class IdpSessions implements IdpSessionsMBean {

    @Inject
    protected IdpSessionStore idpSessionStore;

    protected Gson gson = new Gson();

    @Override
    public String getSessionInfo(String sessionId) {
        IdpSessionStore.IdpSessionInfo session = idpSessionStore.getSessionInfo(sessionId);
        if (session == null) {
            return "Session not found";
        }
        return gson.toJson(session);
    }

    @Override
    public String activateSessionTicket(String serviceProviderTicket) {
        IdpSession session = idpSessionStore.activateSessionTicket(serviceProviderTicket);
        if (session == null) {
            return "Session not found";
        }
        return gson.toJson(session);
    }

    @Override
    public String createServiceProviderTicket(String sessionId) {
        String serviceProviderTicket = idpSessionStore.createServiceProviderTicket(sessionId);
        if (serviceProviderTicket == null) {
            return "Session not found";
        }
        return serviceProviderTicket;
    }

    @Override
    public String removeSession(String sessionId) {
        boolean removed = idpSessionStore.removeSession(sessionId);

        return removed ? "Session removed" : "Session not found";
    }

    @Override
    public List<String> getSessions() {
        List<IdpSessionStore.IdpSessionInfo> sessions = idpSessionStore.getSessions();
        return sessions.stream()
                .map(IdpSessionStore.IdpSessionInfo::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTickets() {
        Map<String, IdpSessionStore.IdpSessionTicketInfo> tickets = idpSessionStore.getTickets();

        return tickets.entrySet().stream()
                .map(entry -> {
                    String sessionId = entry.getValue().getSessionId();
                    long createTs = entry.getValue().getCreateTs();

                    return String.format("Ticket: %s Session: %s Since: %s", entry.getKey(), sessionId, createTs);
                })
                .collect(Collectors.toList());
    }
}
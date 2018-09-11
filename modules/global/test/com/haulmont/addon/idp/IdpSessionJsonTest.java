package com.haulmont.addon.idp;

import com.google.gson.Gson;
import com.haulmont.addon.idp.security.global.IdpSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class IdpSessionJsonTest {

    @Test
    public void idpSessionToJson() {
        IdpSession session = new IdpSession(UUID.randomUUID().toString().replace("-", ""));

        session.setAttributes(new HashMap<>());
        session.getAttributes().put("demo1", 1);
        session.getAttributes().put("demo2", "test");
        session.getAttributes().put("demo3", 2.2);

        String json = new Gson().toJson(session);
        assertNotNull(json);
    }

    @Test
    public void idpSessionFromJson() {
        String sessionJson = "{\"id\":\"ba8693d910404111b3eac8636192d1ff\"," +
                              "\"attributes\":{\"demo3\":2.2,\"demo1\":1,\"demo2\":\"test\"}}";

        IdpSession session = new Gson().fromJson(sessionJson, IdpSession.class);
        assertNotNull(session);
    }
}
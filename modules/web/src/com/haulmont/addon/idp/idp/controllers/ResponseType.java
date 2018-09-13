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

package com.haulmont.addon.idp.idp.controllers;

/**
 * Available IDP ticket response types.
 */
public enum ResponseType {

    /**
     * IDP ticket is sent using URL hash: {@code //service-provider-url#idp_ticket=value}
     */
    CLIENT_TICKET("client-ticket"),
    /**
     * IDP ticket is sent using URL parameter: {@code //service-provider-url?idp_ticket=value}
     */
    SERVER_TICKET("server-ticket");

    protected String code;

    ResponseType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
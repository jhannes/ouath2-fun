package com.johannesbrodwall.oauth2fun.client;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.oauth.OauthProviderSession;

import lombok.SneakyThrows;

public class ClientUserSession {

    private OauthProviderSession providerSession = new OauthProviderSession("demo");

    public JsonObject toJSON(String redirectUri) {
        return providerSession.toJSON(redirectUri);
    }

    @SneakyThrows
    public void fetchAuthToken(String code, String redirectUri) {
        providerSession.fetchAuthToken(code, redirectUri);

    }

    public void setErrorMessage(String errorMessage) {
        providerSession.setErrorMessage(errorMessage);
    }

}

package com.johannesbrodwall.oauth2fun.client;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.oauth.OAuthProvider;
import com.johannesbrodwall.oauth2fun.lib.oauth.OauthProviderSession;

import lombok.SneakyThrows;

public class ClientUserSession {

    private OauthProviderSession providerSession = createDemoSession();

    private OauthProviderSession createDemoSession() {
        OAuthProvider provider = new OAuthProvider("demo");
        provider.setAuthUrl("http://localhost:11080/ident/auth");
        provider.setTokenUrl("http://localhost:11080/ident/token");
        return new OauthProviderSession(provider);
    }

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

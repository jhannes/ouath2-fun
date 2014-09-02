package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import lombok.Setter;

public class OauthProviderSession {

    protected final OAuthProvider provider;

    protected String username;

    @Setter
    private String errorMessage;

    protected String accessToken;

    public OauthProviderSession(String providerName) {
        provider = new OAuthProvider(providerName);
    }

    public boolean isLoggedIn() {
        return accessToken != null;
    }

    public void fetchAuthToken(String code, String redirectUri) throws IOException {
        JsonObject tokenResponse = HttpUtils.executeJsonPostRequest(
                new URL(provider.getTokenUrl()),
                provider.getTokenRequestPayload(code, redirectUri));
        String idToken = tokenResponse.get("id_token").asString();
        JsonObject payload = JsonObject.readFrom(new String(Base64.getDecoder().decode(idToken.split("\\.")[1])));
        username = payload.get("email").asString();
        accessToken = tokenResponse.get("access_token").asString();
    }

    public void fetchProfile() throws IOException {
    }


    public JsonObject toJSON(String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("provider", provider.toJSON(redirectUri));
        result.set("loggedIn", isLoggedIn());
        result.set("username", username);
        if (errorMessage != null) {
            result.set("errorMessage", errorMessage);
            errorMessage = null;
        }
        return result;
    }
}

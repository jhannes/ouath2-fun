package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OauthProviderSession {

    protected final OAuthProvider provider;

    @Getter
    protected String username;

    @Setter
    private String errorMessage;

    protected String accessToken;

    public OauthProviderSession(OAuthProvider provider) {
        this.provider = provider;
    }

    public boolean isLoggedIn() {
        return accessToken != null;
    }

    public void fetchAuthToken(String code, String redirectUri) throws IOException {
        JsonObject tokenResponse = HttpUtils.executeJsonPostRequest(
                new URL(provider.getTokenUrl()),
                provider.getTokenRequestPayload(code, redirectUri));
        String idToken = tokenResponse.get("id_token").asString();
        String idTokenPayload = new String(Base64.getDecoder().decode(idToken.split("\\.")[1]));
        log.info("ID token: {}", idTokenPayload);
        JsonObject payload = JsonObject.readFrom(idTokenPayload);
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

    public static OauthProviderSession createGoogleSession() {
        OAuthProvider provider = new OAuthProvider("google");
        provider.setClientSignup("https://console.developers.google.com/project");
        provider.setAuthUrl("https://accounts.google.com/o/oauth2/auth");
        provider.setTokenUrl("https://accounts.google.com/o/oauth2/token");
        provider.setScope("profile email");
        return new OauthProviderSession(provider);
    }

    public static OauthProviderSession createFacebookSession() {
        OAuthProvider provider = new OAuthProvider("facebook");
        provider.setClientSignup("https://developers.facebook.com/");
        provider.setAuthUrl("https://www.facebook.com/dialog/oauth");
        provider.setTokenUrl("https://graph.facebook.com/oauth/access_token");
        provider.setScope("email");
        return new FacebookOauthProviderSession(provider);
    }
}

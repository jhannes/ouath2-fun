package com.johannesbrodwall.oauth2fun.ident;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.Value;

public class OAuthProvider {

    private String getProperty(String property, String defaultValue) {
        return IdentAppConfiguration.getProperty("oauth2." + providerName + "." + property, defaultValue);
    }

    private String getRequiredProperty(String property) {
        return IdentAppConfiguration.getRequiredProperty("oauth2." + providerName + "." + property);
    }


    @Getter
    private String providerName;

    public OAuthProvider(String providerName) {
        this.providerName = providerName;
    }

    public String getDisplayName() {
        return getProperty("displayName", providerName);
    }

    public String getClientSignup() {
        return getProperty("clientSignup", null);
    }

    @SneakyThrows
    public String getUrl(String redirectUrl) {
        if (getClientId() == null || getAuthUrl() == null) return null;

        return getAuthUrl() + "?"
            + "response_type=code&"
            + "client_id=" + getClientId() + "&"
            + "redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8") + "&"
            + "scope=" + getScope() + "&"
            + "state=" + providerName;
    }

    private String getScope() {
        return getProperty("scope", "profile");
    }

    private String getClientId() {
        return getProperty("clientId", null);
    }

    private String getClientSecret() {
        return getProperty("clientSecret", null);
    }

    private String getAuthUrl() {
        return getProperty("authUrl", null);
    }

    protected String getTokenUrl() {
        return getRequiredProperty("tokenUrl");
    }

    public String getTokenRequestPayload(String code, String redirectUri) {
        return ("code=" + code + "&"
                + "client_id=" + getClientId() + "&"
                + "client_secret=" + getClientSecret() + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "grant_type=authorization_code");
    }

    @Value
    static class TokenResponse {
        private String username;
        private String authToken;
    }

    protected TokenResponse fetchToken(String code, String redirectUri) throws IOException {
        JsonObject tokenResponse = HttpUtils.executeJsonPostRequest(new URL(getTokenUrl()),
                getTokenRequestPayload(code, redirectUri));
        String idToken = tokenResponse.get("id_token").asString();
        JsonObject payload = JsonObject.readFrom(new String(Base64.getDecoder().decode(idToken.split("\\.")[1])));
        String username = payload.get("email").asString();

        return new TokenResponse(username, tokenResponse.get("access_token").asString());
    }

    public String fetchProfile(String accessToken) throws IOException {
        return null;
    }
}

package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;

import java.net.URLEncoder;

import lombok.Getter;
import lombok.SneakyThrows;

public class OAuthProvider {

    private String getProperty(String property, String defaultValue) {
        return OauthConfiguration.getProperty("oauth2." + providerName + "." + property, defaultValue);
    }

    private String getRequiredProperty(String property) {
        return OauthConfiguration.getRequiredProperty("oauth2." + providerName + "." + property);
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

    public String getTokenUrl() {
        return getRequiredProperty("tokenUrl");
    }

    public String getTokenRequestPayload(String code, String redirectUri) {
        return ("code=" + code + "&"
                + "client_id=" + getClientId() + "&"
                + "client_secret=" + getClientSecret() + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "grant_type=authorization_code");
    }

    public JsonObject toJSON(String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("providerName", getProviderName());
        result.set("displayName", getDisplayName());
        result.set("clientSignup", getClientSignup());
        result.set("url", getUrl(redirectUri));
        return result;
    }
}

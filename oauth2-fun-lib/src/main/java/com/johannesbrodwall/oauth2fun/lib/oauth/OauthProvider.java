package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;

import java.net.URLEncoder;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class OauthProvider {

    private String getProperty(String property, String defaultValue) {
        return OauthConfiguration.getProperty("oauth2." + providerName + "." + property, defaultValue);
    }

    @Getter
    private String providerName;

    @Getter @Setter
    private String clientSignup;

    @Getter @Setter
    private String scope;

    @Getter @Setter
    private String authUrl;

    @Getter @Setter
    private String tokenUrl;

    @Getter @Setter
    private String profileUrl;

    public OauthProvider(String providerName) {
        this.providerName = providerName;
    }

    @SneakyThrows
    private String getAuthUrl(String redirectUrl) {
        if (getClientId() == null || getAuthUrl() == null) return null;

        return getAuthUrl() + "?"
            + "response_type=code&"
            + "client_id=" + getClientId() + "&"
            + "redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8") + "&"
            + "scope=" + getScope() + "&"
            + "state=" + providerName;
    }

    private String getClientId() {
        return getProperty("clientId", null);
    }

    private String getClientSecret() {
        return getProperty("clientSecret", null);
    }

    String getTokenRequestPayload(String code, String redirectUri) {
        return ("code=" + code + "&"
                + "client_id=" + getClientId() + "&"
                + "client_secret=" + getClientSecret() + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "grant_type=authorization_code");
    }

    JsonObject toJSON(String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("providerName", getProviderName());
        result.set("displayName", providerName);
        result.set("clientSignup", getClientSignup());
        result.set("url", getAuthUrl(redirectUri));
        return result;
    }

}

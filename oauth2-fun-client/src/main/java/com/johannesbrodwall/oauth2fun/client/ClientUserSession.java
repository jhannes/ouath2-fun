package com.johannesbrodwall.oauth2fun.client;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;
import com.johannesbrodwall.oauth2fun.lib.oauth.OauthConfiguration;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import lombok.SneakyThrows;

public class ClientUserSession {

    private String getProperty(String property, String defaultValue) {
        return OauthConfiguration.getProperty("oauth2.demo." + property, defaultValue);
    }

    private String getRequiredProperty(String property) {
        return OauthConfiguration.getRequiredProperty("oauth2.demo." + property);
    }

    private String username;

    private String getClientId() {
        return getProperty("clientId", null);
    }

    private String getAuthUrl() {
        return getProperty("authUrl", null);
    }

    public String getTokenUrl() {
        return getRequiredProperty("tokenUrl");
    }


    @SneakyThrows
    public String getLoginUrl(String redirectUrl) {
        if (getClientId() == null || getAuthUrl() == null) return null;

        return getAuthUrl() + "?"
            + "response_type=code&"
            + "client_id=" + getClientId() + "&"
            + "redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8");
    }

    public JsonObject toJSON(String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("username", username);
        result.set("loginUrl", getLoginUrl(redirectUri));
        return result;
    }

    @SneakyThrows
    public void fetchAuthToken(String code, String redirectUri) {
        String tokenRequestPayload = ("code=" + code + "&"
                + "client_id=" + getClientId() + "&"
                + "client_secret=" + getRequiredProperty("clientSecret") + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "grant_type=authorization_code");


        JsonObject tokenResponse = HttpUtils.executeJsonPostRequest(
                new URL(getTokenUrl()),
                tokenRequestPayload);
        String idToken = tokenResponse.get("id_token").asString();
        JsonObject payload = JsonObject.readFrom(new String(Base64.getDecoder().decode(idToken.split("\\.")[1])));
        username = payload.get("email").asString();
        String accessToken = tokenResponse.get("access_token").asString();
        // TODO Auto-generated method stub

    }

    public void setErrorMessage(String errorMessage) {
        // TODO Auto-generated method stub

    }

}

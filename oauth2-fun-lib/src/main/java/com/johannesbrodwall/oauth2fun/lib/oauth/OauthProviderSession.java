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

    protected final OauthProvider provider;

    @Getter
    protected String username;

    protected String fullName;

    @Setter
    private String errorMessage;

    protected String accessToken;

    public OauthProviderSession(OauthProvider provider) {
        this.provider = provider;
    }

    public boolean isLoggedIn() {
        return accessToken != null;
    }

    public void fetchAuthToken(String code, String redirectUri) throws IOException {
        JsonObject tokenResponse = HttpUtils.httpPostJson(
                new URL(provider.getTokenUrl()),
                provider.getTokenRequestPayload(code, redirectUri));
        parseToken(tokenResponse);
    }

    private void parseToken(JsonObject tokenResponse) {
        String idToken = tokenResponse.get("id_token").asString();
        String idTokenPayload = base64Decode(idToken.split("\\.")[1]);
        log.info("ID token: {}", idTokenPayload);
        JsonObject payload = JsonObject.readFrom(idTokenPayload);
        username = payload.get("email").asString();
        accessToken = tokenResponse.get("access_token").asString();
    }

    private String base64Decode(String jwt) {
        return new String(Base64.getDecoder().decode(jwt));
    }

    public void fetchProfile() throws IOException {
        JsonObject object = HttpUtils.httpGetWithToken(provider.getProfileUrl(), accessToken);
        fullName = object.get("displayName").asString();
    }

    public JsonObject toJSON(String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("provider", provider.toJSON(redirectUri));
        result.set("loggedIn", isLoggedIn());
        result.set("username", username);
        result.set("name", fullName);
        if (errorMessage != null) {
            result.set("errorMessage", errorMessage);
            errorMessage = null;
        }
        return result;
    }

    public static OauthProviderSession createGoogleSession() {
        OauthProvider provider = new OauthProvider("google");
        provider.setClientSignup("https://console.developers.google.com/project");
        provider.setAuthUrl("https://accounts.google.com/o/oauth2/auth");
        provider.setTokenUrl("https://accounts.google.com/o/oauth2/token");
        provider.setProfileUrl("https://www.googleapis.com/plus/v1/people/me");
        provider.setSignupPicture("sign-in-with-google.png");
        provider.setScope("profile email");
        return new OauthProviderSession(provider);
    }

    public static OauthProviderSession createFacebookSession() {
        OauthProvider provider = new OauthProvider("facebook");
        provider.setClientSignup("https://developers.facebook.com/");
        provider.setAuthUrl("https://www.facebook.com/dialog/oauth");
        provider.setTokenUrl("https://graph.facebook.com/oauth/access_token");
        provider.setProfileUrl("https://graph.facebook.com/me");
        provider.setSignupPicture("login-facebook.png");
        provider.setScope("email");
        return new FacebookOauthProviderSession(provider);
    }
}

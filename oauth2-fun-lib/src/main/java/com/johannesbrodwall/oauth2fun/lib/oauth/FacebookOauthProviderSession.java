package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class FacebookOauthProviderSession extends OauthProviderSession {

    FacebookOauthProviderSession(OAuthProvider provider) {
        super(provider);
    }

    @Override
    public void fetchAuthToken(String code, String redirectUri) throws IOException {
        URL requestUrl = new URL(provider.getTokenUrl() + "?" +
                provider.getTokenRequestPayload(code, redirectUri));
        String response = HttpUtils.executeStringGetRequest(requestUrl);
        Map<String, String> properties = HttpUtils.parseQuery(response);
        accessToken = properties.get("access_token");
    }


    @Override
    public void fetchProfile() throws IOException {
        URL requestUrl = new URL("https://graph.facebook.com/me?access_token=" + accessToken);
        String response = HttpUtils.executeStringGetRequest(requestUrl);
        JsonObject object = JsonObject.readFrom(response);
        username = object.get("email").asString();
    }

}

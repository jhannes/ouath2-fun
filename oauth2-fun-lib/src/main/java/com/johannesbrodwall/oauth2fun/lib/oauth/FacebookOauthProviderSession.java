package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.util.Map;

class FacebookOauthProviderSession extends OauthProviderSession {

    FacebookOauthProviderSession(OauthProvider provider) {
        super(provider);
    }

    @Override
    public void fetchAuthToken(String code, String redirectUri) throws IOException {
        String requestUrl = provider.getTokenUrl() + "?" +
                provider.getTokenRequestPayload(code, redirectUri);
        String response = HttpUtils.httpGetString(requestUrl);
        Map<String, String> properties = HttpUtils.parseQuery(response);
        accessToken = properties.get("access_token");
    }


    @Override
    public void fetchProfile() throws IOException {
        String requestUrl = provider.getProfileUrl() + "?access_token=" + accessToken;
        JsonObject object = HttpUtils.httpGetJson(requestUrl);
        username = object.get("email").asString();
        String firstName = object.get("first_name").asString();
        String lastName = object.get("last_name").asString();
        fullName = firstName + " " + lastName;
    }

}

package com.johannesbrodwall.oauth2fun.ident;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.HttpUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FacebookOauthProvider extends OAuthProvider {

    public FacebookOauthProvider() {
        super("facebook");
    }


    @Override
    protected TokenResponse fetchToken(String code, String redirectUri) throws IOException {
        URL requestUrl = new URL(getTokenUrl() + "?" + getTokenRequestPayload(code, redirectUri));
        String response = HttpUtils.executeStringGetRequest(requestUrl);

        log.debug("fetchToken {} -> {}", requestUrl, response);

        String[] propertyValues = response.split("&");
        Map<String,String> properties = new HashMap<String, String>();
        for (String property : propertyValues) {
            properties.put(property.substring(0, property.indexOf('=')),
                    property.substring(property.indexOf('=') + 1));
        }

        return new TokenResponse(null, properties.get("access_token"));
    }

    @Override
    public String fetchProfile(String accessToken) throws IOException {
        URL requestUrl = new URL("https://graph.facebook.com/me?access_token=" + accessToken);
        String response = HttpUtils.executeStringGetRequest(requestUrl);
        JsonObject object = JsonObject.readFrom(response);
        return object.get("email").asString();
    }

}

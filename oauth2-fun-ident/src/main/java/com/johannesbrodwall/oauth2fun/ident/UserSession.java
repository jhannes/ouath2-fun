package com.johannesbrodwall.oauth2fun.ident;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.oauth.FacebookOauthProviderSession;
import com.johannesbrodwall.oauth2fun.lib.oauth.OauthProviderSession;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class UserSession {

    @Getter
    private Map<String, OauthProviderSession> providerSessions = new HashMap<String, OauthProviderSession>();

    public UserSession() {
        providerSessions.put("google", new OauthProviderSession("google"));
        providerSessions.put("facebook", new FacebookOauthProviderSession());
    }

    public JsonObject toJSON(String redirectUri) {
        JsonObject userinfo = new JsonObject();
        JsonArray providerSessions = new JsonArray();
        for (OauthProviderSession providerSession : getProviderSessions().values()) {
            providerSessions.add(providerSession.toJSON(redirectUri));
        }
        userinfo.set("providers", providerSessions);

        return userinfo;
    }

}

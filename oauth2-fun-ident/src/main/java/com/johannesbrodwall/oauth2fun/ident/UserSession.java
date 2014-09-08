package com.johannesbrodwall.oauth2fun.ident;

import org.json.JSONArray;
import org.json.JSONObject;

import com.johannesbrodwall.oauth2fun.lib.oauth.OauthProviderSession;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class UserSession {

    @Getter
    private Map<String, OauthProviderSession> providerSessions = new HashMap<>();

    private Map<String, OauthClientSession> clientSessions = new HashMap<>();

    private OauthClientSession pendingClientSession;

    public UserSession() {
        providerSessions.put("google", OauthProviderSession.createGoogleSession());
        providerSessions.put("facebook", OauthProviderSession.createFacebookSession());
    }

    public JSONObject toJSON(String redirectUri) {
        JSONObject userinfo = new JSONObject();
        JSONArray providerSessions = new JSONArray();
        for (OauthProviderSession providerSession : getProviderSessions().values()) {
            providerSessions.put(providerSession.toJSON(redirectUri));
        }
        userinfo.put("providers", providerSessions);

        return userinfo;
    }

    public String startClientSession(String clientId, String redirectUri) {
        this.pendingClientSession = new OauthClientSession();
        pendingClientSession.setRedirectUri(redirectUri);
        clientSessions.put(clientId, pendingClientSession);
        return pendingClientSession.getCode();
    }

    public String getEmail() {
        for (OauthProviderSession providerSession : providerSessions.values()) {
            if (providerSession.getUsername() != null) return providerSession.getUsername();
        }
        return null;
    }

    public OauthClientSession getClientSession(String clientId) {
        return clientSessions.get(clientId);
    }

    public boolean isLoggedIn() {
        for (OauthProviderSession providerSession : providerSessions.values()) {
            if (providerSession.isLoggedIn()) return true;
        }
        return false;
    }

    public String takePendingRedirectUri() {
        if (pendingClientSession != null) {
            String result = pendingClientSession.getRedirectUri() + "?code=" + pendingClientSession.getCode();
            pendingClientSession = null;
            return result;
        }
        return null;
    }

}

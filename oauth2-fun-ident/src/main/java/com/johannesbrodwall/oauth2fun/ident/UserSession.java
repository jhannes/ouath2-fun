package com.johannesbrodwall.oauth2fun.ident;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

public class UserSession {

    @Getter
    private Map<String, OAuthProviderSession> providerSessions = new HashMap<String, OAuthProviderSession>();

    public UserSession(List<OAuthProvider> providers) {
        for (OAuthProvider provider : providers) {
            providerSessions.put(provider.getProviderName(), new OAuthProviderSession(provider));
        }
    }

}

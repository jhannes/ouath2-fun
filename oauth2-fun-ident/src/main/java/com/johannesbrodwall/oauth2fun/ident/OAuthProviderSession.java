package com.johannesbrodwall.oauth2fun.ident;

import com.johannesbrodwall.oauth2fun.ident.OAuthProvider.TokenResponse;

import java.io.IOException;
import java.net.MalformedURLException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class OAuthProviderSession {

    @Getter
    private final OAuthProvider provider;

    @Getter
    private String username;

    @Setter
    private String errorMessage;

    private String accessToken;

    public String takeErrorMessage() {
        String result = errorMessage;
        errorMessage = null;
        return result;
    }


    public void fetchAuthToken(String code, String redirectUri) throws MalformedURLException, IOException {
        TokenResponse tokenResponse = provider.fetchToken(code, redirectUri);
        username = tokenResponse.getUsername();
        accessToken = tokenResponse.getAuthToken();
    }

    public boolean isLoggedIn() {
        return accessToken != null;
    }

    public void fetchProfile() throws IOException {
        username = provider.fetchProfile(accessToken);
    }

}

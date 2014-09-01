package com.johannesbrodwall.oauth2fun.ident.web;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.johannesbrodwall.oauth2fun.ident.FacebookOauthProvider;
import com.johannesbrodwall.oauth2fun.ident.OAuthProvider;
import com.johannesbrodwall.oauth2fun.ident.OAuthProviderSession;
import com.johannesbrodwall.oauth2fun.ident.UserSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserinfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        JsonObject userinfo = getUserinfo(getUserSession(req), getRedirectUri(req));
        try (PrintWriter writer = resp.getWriter()) {
            userinfo.writeTo(writer);
        }
        resp.setContentType("application/json");
    }

    private String getRedirectUri(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/oauth2callback";
    }

    private JsonObject getUserinfo(UserSession userSession, String redirectUri) {
        JsonObject userinfo = new JsonObject();
        userinfo.set("providers",
                toJsonArray(userSession.getProviderSessions().values().stream().map(p -> toJSON(p, redirectUri))));

        return userinfo;
    }

    private JsonObject toJSON(OAuthProviderSession providerSession, String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("provider", toJSON(providerSession.getProvider(), redirectUri));
        result.set("loggedIn", providerSession.isLoggedIn());
        result.set("username", providerSession.getUsername());
        result.set("errorMessage", providerSession.takeErrorMessage());
        return result;
    }

    private JsonObject toJSON(OAuthProvider provider, String redirectUri) {
        JsonObject result = new JsonObject();
        result.set("providerName", provider.getProviderName());
        result.set("displayName", provider.getDisplayName());
        result.set("clientSignup", provider.getClientSignup());
        result.set("url", provider.getUrl(redirectUri));
        return result;
    }

    private JsonArray toJsonArray(Stream<? extends JsonValue> objects) {
        JsonArray result = new JsonArray();
        objects.forEach(o -> result.add(o));
        return result;
    }

    private UserSession getUserSession(HttpServletRequest req) {
        UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");
        if (userSession == null) {
            userSession = new UserSession(getProviders());
            req.getSession().setAttribute("userSession", userSession);
        }
        return userSession;
    }

    private List<OAuthProvider> getProviders() {
        return Arrays.asList(new OAuthProvider("google"), new FacebookOauthProvider());
    }

}

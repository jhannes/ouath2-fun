package com.johannesbrodwall.oauth2fun.ident.web;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.ident.UserSession;
import com.johannesbrodwall.oauth2fun.lib.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OauthProviderServlet extends HttpServlet {

    private static Map<String, UserSession> startedSessions = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSession userSession = ServletUtils.getSessionObject(UserSession.class, req);

        String clientId = req.getParameter("client_id");
        String code = userSession.startClientSession(clientId, req.getParameter("redirect_uri"));
        startedSessions.put(code + clientId, userSession);

        if (userSession.isLoggedIn()) {
            resp.sendRedirect(userSession.takePendingRedirectUri());
            return;
        }

        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String clientId = req.getParameter("client_id");
        String clientSecret = req.getParameter("client_secret");

        if (!(clientId.equals("test") && clientSecret.equals("secret"))) {
            throw new RuntimeException("Invalid client credentials");
        }

        String code = req.getParameter("code");
        UserSession userSession = startedSessions.get(code + clientId);
        userSession.getClientSession(clientId).redeemCode(code);

        JsonObject idToken = new JsonObject();
        idToken.set("email", userSession.getEmail());

        JsonObject response = new JsonObject();
        response.set("id_token", "a." + Base64.getEncoder().encodeToString(idToken.toString().getBytes())
             + ".dsgsg");
        response.set("access_token", userSession.getClientSession(clientId).getAccessToken());

        resp.setContentType("text/json");
        try (PrintWriter writer = resp.getWriter()) {
            response.writeTo(writer);
        }
    }

}

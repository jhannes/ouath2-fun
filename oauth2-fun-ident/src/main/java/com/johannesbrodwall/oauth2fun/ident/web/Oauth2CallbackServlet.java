package com.johannesbrodwall.oauth2fun.ident.web;

import com.johannesbrodwall.oauth2fun.ident.UserSession;
import com.johannesbrodwall.oauth2fun.lib.ServletUtils;
import com.johannesbrodwall.oauth2fun.lib.oauth.OauthProviderSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Oauth2CallbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(ServletUtils.getRequestUrl(req));

        UserSession userSession = ServletUtils.getSessionObject(UserSession.class, req);
        OauthProviderSession providerSession = userSession.getProviderSessions().get(req.getParameter("state"));

        if (req.getParameter("error_message") != null) {
            providerSession.setErrorMessage(req.getParameter("error_message"));
            resp.sendRedirect("/");
            return;
        }
        if (req.getParameter("error") != null) {
            providerSession.setErrorMessage(req.getParameter("error"));
            resp.sendRedirect("/");
            return;
        }

        try {
            String redirectUri = ServletUtils.getContextUrl(req) + "/oauth2callback";
            providerSession.fetchAuthToken(req.getParameter("code"), redirectUri);

            String pendingRedirectUri = userSession.takePendingRedirectUri();
            String redirect = pendingRedirectUri != null ? pendingRedirectUri : "/";

            resp.sendRedirect(redirect);
        } catch (Exception e) {
            log.warn(req.getParameter("state") + " callback failed", e);
            providerSession.setErrorMessage("Unexpected error: " + e);
            resp.sendRedirect("/");
        }
    }
}

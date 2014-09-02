package com.johannesbrodwall.oauth2fun.ident.web;

import com.johannesbrodwall.oauth2fun.ident.UserSession;
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
        UserSession userSession = getUserSession(req);
        OauthProviderSession providerSession = userSession.getProviderSessions().get(req.getParameter("state"));

        try {
            providerSession.fetchAuthToken(req.getParameter("code"), getRedirectUri(req));
            resp.sendRedirect("/");
        } catch (Exception e) {
            log.warn(req.getParameter("state") + " callback failed", e);
            providerSession.setErrorMessage("Unexpected error: " + e);
            resp.sendRedirect("/");
        }
    }

    private String getRedirectUri(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/oauth2callback";
    }

    private UserSession getUserSession(HttpServletRequest req) {
        return (UserSession) req.getSession().getAttribute("userSession");
    }
}

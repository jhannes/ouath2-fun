package com.johannesbrodwall.oauth2fun.client.web;


import com.johannesbrodwall.oauth2fun.client.ClientUserSession;

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
        ClientUserSession userSession = getUserSession(req);

        try {
            userSession.fetchAuthToken(req.getParameter("code"), getRedirectUri(req));
            resp.sendRedirect("/");
        } catch (Exception e) {
            log.warn(req.getParameter("state") + " callback failed", e);
            userSession.setErrorMessage("Unexpected error: " + e);
            resp.sendRedirect("/");
        }
    }

    private String getRedirectUri(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/oauth2callback";
    }

    private ClientUserSession getUserSession(HttpServletRequest req) {
        ClientUserSession userSession = (ClientUserSession) req.getSession().getAttribute("userSession");
        if (userSession == null) {
            userSession = new ClientUserSession();
            req.getSession().setAttribute("userSession", userSession);
        }
        return userSession;
    }
}

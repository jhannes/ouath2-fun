package com.johannesbrodwall.oauth2fun.ident.web;

import com.johannesbrodwall.oauth2fun.ident.UserSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserinfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserSession userSession = getUserSession(req);
        try (PrintWriter writer = resp.getWriter()) {
            userSession.toJSON(getRedirectUri(req)).writeTo(writer);
        }
        resp.setContentType("application/json");
    }

    private String getRedirectUri(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/oauth2callback";
    }

    private UserSession getUserSession(HttpServletRequest req) {
        UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");
        if (userSession == null) {
            userSession = new UserSession();
            req.getSession().setAttribute("userSession", userSession);
        }
        return userSession;
    }

}

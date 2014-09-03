package com.johannesbrodwall.oauth2fun.ident.web;

import com.johannesbrodwall.oauth2fun.ident.UserSession;
import com.johannesbrodwall.oauth2fun.lib.ServletUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FetchProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSession userSession = ServletUtils.getSessionObject(UserSession.class, req);
        userSession.getProviderSessions().get(req.getParameter("provider")).fetchProfile();
        resp.sendRedirect("/");
    }
}

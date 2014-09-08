package com.johannesbrodwall.oauth2fun.client.web;



import com.johannesbrodwall.oauth2fun.client.ClientUserSession;
import com.johannesbrodwall.oauth2fun.lib.ServletUtils;

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
        ClientUserSession userSession = ServletUtils.getSessionObject(ClientUserSession.class, req);
        try (PrintWriter writer = resp.getWriter()) {
            String redirectUri = ServletUtils.getRequestUrl(req) + "/oauth2callback";
            writer.write(userSession.toJSON(redirectUri).toString());
        }
        resp.setContentType("application/json");
    }

}

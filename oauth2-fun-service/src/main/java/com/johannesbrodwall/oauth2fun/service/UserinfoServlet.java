package com.johannesbrodwall.oauth2fun.service;

import com.eclipsesource.json.JsonObject;
import com.johannesbrodwall.oauth2fun.lib.ServletUtils;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserinfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LoginSession session = ServletUtils.getSessionObject(LoginSession.class, req);
        JsonObject userinfo = new JsonObject();
        userinfo.set("user", session.toJSON());

        resp.setContentType("application/json");

        try (Writer writer = resp.getWriter()) {
            userinfo.writeTo(writer);
        }
    }

}

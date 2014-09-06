package com.johannesbrodwall.oauth2fun.service;

import com.johannesbrodwall.oauth2fun.lib.ServletUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        LoginSession session = ServletUtils.getSessionObject(LoginSession.class, req);
        session.setUsername(req.getParameter("username"));

        resp.sendRedirect(req.getContextPath());

    }
}

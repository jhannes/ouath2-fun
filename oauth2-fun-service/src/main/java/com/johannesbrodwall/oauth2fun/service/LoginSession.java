package com.johannesbrodwall.oauth2fun.service;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

public class LoginSession {

    @Getter @Setter
    private String username;

    JSONObject toJSON() {
        return new JSONObject()
            .put("username", username)
            .put("name", username);
    }
}

package com.johannesbrodwall.oauth2fun.service;

import com.eclipsesource.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class LoginSession {

    @Getter @Setter
    private String username;

    JsonObject toJSON() {
        return new JsonObject()
            .set("username", username)
            .set("name", username);
    }
}

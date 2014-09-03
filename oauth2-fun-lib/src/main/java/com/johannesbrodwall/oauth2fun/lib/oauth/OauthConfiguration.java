package com.johannesbrodwall.oauth2fun.lib.oauth;

import com.johannesbrodwall.oauth2fun.lib.AppConfiguration;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j class OauthConfiguration extends AppConfiguration {

    private static OauthConfiguration instance = new OauthConfiguration("oauth2.ident.properties");

    private OauthConfiguration(String filename) {
        file = new File(filename);
    }

    static String getRequiredProperty(String propertyName) {
        String result = instance.getProperty(propertyName);
        if (result == null) {
            throw new RuntimeException("Missing property " + propertyName);
        }
        return result;
    }

    static String getProperty(String propertyName, String defaultValue) {
        String result = instance.getProperty(propertyName);
        if (result == null) {
            log.debug("Missing property {} in {}", propertyName);
            return defaultValue;
        }
        return result;
    }

}

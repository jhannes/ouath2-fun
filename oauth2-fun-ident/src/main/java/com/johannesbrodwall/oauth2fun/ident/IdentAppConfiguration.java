package com.johannesbrodwall.oauth2fun.ident;

import com.johannesbrodwall.oauth2fun.lib.AppConfiguration;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentAppConfiguration extends AppConfiguration {

    private static IdentAppConfiguration instance = new IdentAppConfiguration("oauth2.ident.properties");

    public IdentAppConfiguration(String filename) {
        file = new File(filename);
    }

    public static String getRequiredProperty(String propertyName) {
        String result = instance.getProperty(propertyName);
        if (result == null) {
            throw new RuntimeException("Missing property " + propertyName);
        }
        return result;
    }

    public static String getProperty(String propertyName, String defaultValue) {
        String result = instance.getProperty(propertyName);
        if (result == null) {
            log.debug("Missing property {} in {}", propertyName);
            return defaultValue;
        }
        return result;
    }

}

package com.johannesbrodwall.oauth2fun.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfiguration {

    private long nextCheckTime = 0;
    private long lastLoadTime = 0;
    private Properties properties = new Properties();
    protected File file;

    protected String getProperty(String propertyName) {
        ensureConfigurationIsFresh();
        return properties.getProperty(propertyName);
    }

    private synchronized void ensureConfigurationIsFresh() {
        if (System.currentTimeMillis() < nextCheckTime) return;
        nextCheckTime = System.currentTimeMillis() + 10000;
        log.trace("Rechecking {}", file);

        if (lastLoadTime >= file.lastModified()) return;
        lastLoadTime = file.lastModified();
        log.debug("Reloading {}", file);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            properties.clear();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
package com.majorMedia.BackOfficeDashboard.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentUtil {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static String get(String key) {
        return dotenv.get(key);
    }

    public static String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }
}

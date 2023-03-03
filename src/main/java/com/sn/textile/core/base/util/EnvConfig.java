package com.sn.textile.core.base.util;
/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

public class EnvConfig {

    public static String getString(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }

        return defaultValue;
    }

    public static long getLong(String key, long defaultValue) {
        try {
            String value = System.getenv(key);
            if (value != null) {
                return Long.parseLong(value);
            }
        } catch (Exception e) {}
        return defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            String value = System.getenv(key);
            if (value != null) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {}
        return defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            String value = System.getenv(key);
            if (value != null) {
                return Boolean.parseBoolean(value);
            }
        } catch (Exception e) {}
        return defaultValue;
    }

}

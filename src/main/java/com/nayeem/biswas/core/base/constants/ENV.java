package com.nayeem.biswas.core.base.constants;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 06, 2023
 */

public class ENV {

    public static final String SPRING_ACTIVE_PROFILE = "SPRING_ACTIVE_PROFILE";
    public static final String DEF_API_KEY = "DEF_API_KEY";
    public static final String DEF_API_SEC = "DEF_API_SEC";
    public static final String SERVER_PORT = "SERVER_PORT";
    public static final String SERVER_CONTEXT_PATH = "SERVER_CONTEXT_PATH";
    public static final String TOKEN_EXPIRY_TIME_IN_MIN = "TOKEN_EXPIRY_TIME_IN_MIN";
    public static final String TOKEN_PRIVATE_KEY = "TOKEN_PRIVATE_KEY";
    public static final String HIBERNATE_DDL_AUTO = "hibernate_ddl_auto";
    public static final String HIBERNATE_SHOW_SQL = "hibernate_show_sql";
    public static final String HIBERNATE_DIALECT = "hibernate_dialect";
    public static final String SWAGGER_UI_PATH = "swagger_ui_path";

    public static final String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT"; // IN MILLISECONDS
    public static final String CONNECTION_MAX_POOL_SIZE = "CONNECTION_MAX_POOL_SIZE"; // IN NUMBER
    public static final String CONNECTION_MIN_IDLE_TIME = "CONNECTION_MIN_IDLE_TIME"; // IN MINUTES


    public static final String OTP_EXPIRY_TIME_IN_MIN = "OTP_EXPIRY_TIME_IN_MIN";
    public static final String LOGIN_BLOCK_DURATION_IN_MIN = "LOGIN_BLOCK_DURATION_IN_MIN";
    public static final String ALLOWED_MAX_FAILED_LOGIN = "ALLOWED_MAX_FAILED_LOGIN";
    public static final String DEFAULT_SUPERADMIN_TOKEN = "b293a2f11b659e1d71ce6fca9a09e3bc72df84f432e0c8114c57ad69bbd08a4cSuperadmin";
    public static final String DEFAULT_MERCHANT_TOKEN = "b293a2f11b659e1d71ce6fca9a09e3bc72df84f432e0c8114c57ad69bbd08a4cMerchant";
    public static final String DEFAULT_CUSTOMER_TOKEN = "b293a2f11b659e1d71ce6fca9a09e3bc72df84f432e0c8114c57ad69bbd08a4cCustomer";

    public static class PATH{
        public static final String MERCHANT_HOME = "/home/merchant";
        public static final String CONF_PATH = "/home/cnf";
        public static final String APP_SRC_PATH = "/home/apps_store";
        public static final String THEME_SRC_PATH = "/home/themes_store";
    }

    public static class BUCKET{
        public static final String APP_ICON_PATH = "WEBXPPICONPATH";
        public static final String APP_SCREENSHOT_PATH = "WEBXAPPSCREENSHOTPATH";

        public static final String THEME_ICON_PATH = "WEBXTHEMEICONPATH";
        public static final String THEME_SCREENSHOT_PATH = "WEBXTHEMESCREENSHOTPATH";
    }

    public static class DB{

        public static class AUTH{
            public static final String USERNAME = "AUTH_DB_USERNAME";
            public static final String PASSWORD = "AUTH_DB_PASSWORD";
            public static final String JDBC_URL = "AUTH_DB_JDBC_URL";
            public static final String DRIVER = "AUTH_DB_DRIVER";
        }
        
        public static class CORE{
            public static final String USERNAME = "CORE_DB_USERNAME";
            public static final String PASSWORD = "CORE_DB_PASSWORD";
            public static final String JDBC_URL = "CORE_DB_JDBC_URL";
            public static final String DRIVER = "CORE_DB_DRIVER";
        }

        public static class NOTIFY{
            public static final String USERNAME = "NOTIFY_DB_USERNAME";
            public static final String PASSWORD = "NOTIFY_DB_PASSWORD";
            public static final String JDBC_URL = "NOTIFY_DB_JDBC_URL";
            public static final String DRIVER = "NOTIFY_DB_DRIVER";
        }

        public static class DEVELOPER{
            public static final String USERNAME = "DEVELOPER_DB_USERNAME";
            public static final String PASSWORD = "DEVELOPER_DB_PASSWORD";
            public static final String JDBC_URL = "DEVELOPER_DB_JDBC_URL";
            public static final String DRIVER = "DEVELOPER_DB_DRIVER";
        }

    }

    public static class REDIS{
        public static final String USERNAME = "REDIS_USERNAME";
        public static final String PASSWORD = "REDIS_PASSWORD";
        public static final String PORT = "REDIS_PORT";
        public static final String HOST = "REDIS_HOST";
    }


    public static class MINIO{
        public static final String ACCESS_KEY = "MINIO_ACCESS_KEY";
        public static final String SECRET_KEY = "MINIO_SECRET_KEY";
        public static final String ENDPOINT = "MINIO_ENDPOINT";
        public static final String PORT = "MINIO_PORT";
        public static final String SECURE = "MINIO_IS_SECURE";
    }


}

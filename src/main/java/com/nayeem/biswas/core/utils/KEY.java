
package com.nayeem.biswas.core.utils;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

public class KEY {

    public static final Long SQL_INSERT = 1L;
    public static final Long SQL_UPDATE = 2L;
    public static final Long SQL_DELETE = 3L;
    public static final Long SQL_UNCHANGE = 0L;

    public static final String SESSION_NO = "session_no";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String STORE_ID = "store_id";
    public static final String ROLE_ID = "role_id";
    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String SHOP_NAME = "shop_name";
    public static final String SHOP_PATH = "shop_path";
    public static final String EMAIL = "email";
    public static final String MOBILE_NO = "mobile_no";
    public static final String OTP = "otp";
    public static final String UID = "uid";


    public static class REDIS {
        public static final String SUCCESS_LIST_EXT = "_SUCCESS_LIST";
        public static final String FAILED_LIST_EXT = "_FAILED_LIST";
        public static final String PENDING_EXT = "_PENDING";
        public static final String TOTAL_COUNT_EXT = "_TOTAL_COUNT";
        public static final String USER_EXT = "_USER";
        public static final String START_TIME_EXT = "_START_TIME";
        public static final String END_TIME_EXT = "_END_TIME";
        public static final String PROCESS_FLAG_EXT = "_PROCESS_FLAG";
    }

}

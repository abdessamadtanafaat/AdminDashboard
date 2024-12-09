package com.majorMedia.BackOfficeDashboard.security;


import com.majorMedia.BackOfficeDashboard.config.EnvironmentUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityConstants {
    public static final String SECRET_KEY_STRING = EnvironmentUtil.get("SECRET_KEY");
    public static final byte[] SECRET_KEY_BYTES = SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8);
    public static final SecretKey SECRET_KEY = new SecretKeySpec(SECRET_KEY_BYTES, "AES");
    public static final int TOKEN_EXPIRATION = 86400000   ; //1 JOUR
    public static final int TOKEN_LENGTH = 64;
    public static final int TOKEN_EXPIRATION_EMAIL = 5 ; //Minutes
    public static final String BEARER = "Bearer ";
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String RESET_PASSWORD_URL = EnvironmentUtil.get("RESET_PASSWORD_URL");
    public static final String DEFAULT_PASSWORD = EnvironmentUtil.get("DEFAULT_PASSWORD");
    public static final String[] WHITE_LIST = {
            "auth/reset-password"
            ,"auth/is-token-valid"
            ,"auth/login"
            ,"auth/register"
            ,"auth/password-request"
            ,"/v3/api-docs",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
            ,"/swagger-resources/**"
            ,"/configuration/ui"
            ,"/configuration/security"
            ,"/configuration/security"
            ,"/avatars/**"
            ,"/avatars/**"
            ,"/admin/image/**",
            "/admin/verifyToken/**"
            //,"/super-admin/**" // a supprimer apres
            //,"/admin/**" // a supprimer apres
    };
    public static  final Long MAX_AGE = 3600L;
    public static final  int CORS_FILTER_ORDER =-102;
    public static final String AVATAR_DIRECTORY = EnvironmentUtil.get("AVATAR_DIRECTORY");
    public static final String AVATAR_URL = EnvironmentUtil.get("AVATAR_URL");
}

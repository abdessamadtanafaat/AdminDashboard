package com.majorMedia.BackOfficeDashboard.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final int TOKEN_EXPIRATION = 86400000   ; //1 JOUR
    public static final int TOKEN_LENGTH = 64;
    public static final int TOKEN_EXPIRATION_EMAIL = 5 ; //Minutes
    public static final String BEARER = "Bearer ";
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String RESET_PASSWORD_URL = "http://localhost:3000/reset-password?token=";
    public static final String[] WHITE_LIST = {
            "auth/reset-password"
            ,"/avatars/**"
            ,"auth/is-token-valid"
            ,"auth/login"
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
            ,"/configuration/security",
            "/avatars/**",
            "/admin/image/**"
    };
    public static  final Long MAX_AGE = 3600L;
    public static final  int CORS_FILTER_ORDER =-102;
    public static final String AVATAR_DIRECTORY = "src/main/resources/static/avatars/";
    public static final String AVATAR_URL = "http://localhost:8080/admin/image/";
}

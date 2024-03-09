package com.majorMedia.BackOfficeDashboard.Security;

public class SecurityConstants {
    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final int TOKEN_EXPIRATION = 86400000   ; //1 JOUR
    public static final int TOKEN_LENGTH = 64;
    public static final int TOKEN_EXPIRATION_EMAIL = 5 ; //Minutes
    public static final String BEARER = "Bearer ";
    public static final String AUTHENTICATE_PATH = "/api/v1/auth/authenticate";


}

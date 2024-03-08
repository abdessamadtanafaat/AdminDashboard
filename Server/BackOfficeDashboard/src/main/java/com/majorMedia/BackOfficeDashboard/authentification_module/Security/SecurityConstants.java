package com.majorMedia.BackOfficeDashboard.authentification_module.Security;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
    public static final int TOKEN_EXPIRATION = 86400000 ;

    public static final int TOKEN_EXPIRATION_EMAIL = 5 ; //Minutes

    public static final String BEARER = "Bearer ";
    public static final String AUTHENTICATE_PATH = "/api/v1/auth/authenticate";
}

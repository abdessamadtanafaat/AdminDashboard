package com.majorMedia.BackOfficeDashboard.authentification_module.Exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidPasswordException extends BadCredentialsException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

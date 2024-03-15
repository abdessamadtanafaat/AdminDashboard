package com.majorMedia.BackOfficeDashboard.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidPasswordException extends BadCredentialsException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

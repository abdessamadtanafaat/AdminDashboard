package com.majorMedia.BackOfficeDashboard.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class NotFoundEmailException extends BadCredentialsException {
    public NotFoundEmailException(String email) {
        super("The " + email + " not found.");
    }
}

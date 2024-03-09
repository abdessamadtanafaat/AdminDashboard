package com.majorMedia.BackOfficeDashboard.authentification_module.Exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidEmailException extends BadCredentialsException {
    public InvalidEmailException(String message){
        super(message);
    }
}

package com.majorMedia.BackOfficeDashboard.Exception;

import org.springframework.security.authentication.BadCredentialsException;

public class AlreadyExistEmailException  extends RuntimeException {
    public AlreadyExistEmailException (String email){
        super(email + " already exists.");
    }
}

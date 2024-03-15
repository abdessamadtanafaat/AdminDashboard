package com.majorMedia.BackOfficeDashboard.exception;

public class AlreadyExistEmailException  extends RuntimeException {
    public AlreadyExistEmailException (String email){
        super(email + " already exists.");
    }
}

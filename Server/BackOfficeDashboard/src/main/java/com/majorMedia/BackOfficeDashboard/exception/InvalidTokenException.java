package com.majorMedia.BackOfficeDashboard.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("Invalid Token.");
    }
}

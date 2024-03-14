package com.majorMedia.BackOfficeDashboard.Exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("Invalid Token.");
    }
}
